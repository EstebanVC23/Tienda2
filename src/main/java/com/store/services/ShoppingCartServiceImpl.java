package com.store.services;

import com.store.models.Producto;
import com.store.models.Sale;
import com.store.models.SaleItem;
import com.store.models.SaleStatus;
import com.store.models.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de carrito de compras
 */
public class ShoppingCartServiceImpl implements IShoppingCartService {
    private final ISaleService saleService;
    private final IUsuarioServicio usuarioService;
    
    public ShoppingCartServiceImpl(ISaleService saleService, IUsuarioServicio usuarioService) {
        this.saleService = saleService;
        this.usuarioService = usuarioService;
    }
    
    @Override
    public Sale getOrCreateCart(int userId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
        
        // Si ya tiene un carrito asignado
        if (usuario.getCarritoComprasId() != 0) {
            Sale existingCart = saleService.obtenerVentaPorId(usuario.getCarritoComprasId());
            if (existingCart != null && existingCart.getStatus() == SaleStatus.PENDING) {
                return existingCart;
            }
        }
        
        // Crear nuevo carrito
        Sale newCart = new Sale();
        newCart.setCustomerId(userId);
        newCart.setDate(new Date());
        newCart.setStatus(SaleStatus.PENDING);
        newCart.setItems(new ArrayList<>());
        
        if (saleService.crearVenta(newCart)) {
            usuario.setCarritoComprasId(newCart.getId());
            usuarioService.actualizarUsuario(usuario);
            return newCart;
        }
        
        return null;
    }
    
    @Override
    public boolean addToCart(int userId, Producto producto, int quantity) {
        Sale cart = getOrCreateCart(userId);
        if (cart == null) return false;
        
        Optional<SaleItem> existingItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getCodigo().equals(producto.getCodigo()))
            .findFirst();
        
        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            cart.getItems().add(new SaleItem(producto, quantity));
        }
        
        cart.recalculateTotal();
        return saleService.actualizarVenta(cart);
    }
    
    @Override
    public boolean removeFromCart(int userId, String productCode) {
        Sale cart = getOrCreateCart(userId);
        if (cart == null) return false;
        
        boolean removed = cart.getItems().removeIf(
            item -> item.getProduct().getCodigo().equals(productCode)
        );
        
        if (removed) {
            cart.recalculateTotal();
            return saleService.actualizarVenta(cart);
        }
        
        return false;
    }
    
    @Override
    public boolean completePurchase(int userId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
        if (usuario.getCarritoComprasId() == 0) return false;
        
        Sale cart = saleService.obtenerVentaPorId(usuario.getCarritoComprasId());
        if (cart == null || cart.getStatus() != SaleStatus.PENDING) return false;
        
        cart.setStatus(SaleStatus.COMPLETED);
        boolean success = saleService.actualizarVenta(cart);
        
        if (success) {
            usuario.setCarritoComprasId(0);
            usuarioService.actualizarUsuario(usuario);
        }
        
        return success;
    }
    
    @Override
    public List<SaleItem> getCartItems(int userId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
        if (usuario.getCarritoComprasId() == 0) return new ArrayList<>();
        
        Sale cart = saleService.obtenerVentaPorId(usuario.getCarritoComprasId());
        return cart != null ? cart.getItems() : new ArrayList<>();
    }
    
    @Override
    public Sale getCurrentCart(int userId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(userId);
        if (usuario.getCarritoComprasId() == 0) return null;
        
        return saleService.obtenerVentaPorId(usuario.getCarritoComprasId());
    }
    
    @Override
    public boolean clearCart(int userId) {
        Sale cart = getOrCreateCart(userId);
        if (cart == null) return false;
        
        cart.getItems().clear();
        cart.recalculateTotal();
        return saleService.actualizarVenta(cart);
    }
}