package com.store.services;

import com.store.models.Producto;
import com.store.models.Sale;
import com.store.models.SaleItem;
import java.util.List;

/**
 * Interfaz para el servicio de carrito de compras
 */
public interface IShoppingCartService {
    /**
     * Obtiene o crea el carrito de compras para un usuario
     * @param userId ID del usuario
     * @return El carrito de compras (venta con estado PENDING)
     */
    Sale getOrCreateCart(int userId);
    
    /**
     * Agrega un producto al carrito de compras
     * @param userId ID del usuario
     * @param producto Producto a agregar
     * @param quantity Cantidad a agregar
     * @return true si se agregó correctamente
     */
    boolean addToCart(int userId, Producto producto, int quantity);
    
    /**
     * Elimina un producto del carrito de compras
     * @param userId ID del usuario
     * @param productCode Código del producto a eliminar
     * @return true si se eliminó correctamente
     */
    boolean removeFromCart(int userId, String productCode);
    
    /**
     * Completa la compra (cambia estado a COMPLETED)
     * @param userId ID del usuario
     * @return true si se completó correctamente
     */
    boolean completePurchase(int userId);
    
    /**
     * Obtiene los items del carrito de compras
     * @param userId ID del usuario
     * @return Lista de items en el carrito
     */
    List<SaleItem> getCartItems(int userId);
    
    /**
     * Obtiene el carrito de compras actual del usuario
     * @param userId ID del usuario
     * @return La venta que representa el carrito o null si no existe
     */
    Sale getCurrentCart(int userId);
    
    /**
     * Limpia el carrito de compras (elimina todos los items)
     * @param userId ID del usuario
     * @return true si se limpió correctamente
     */
    boolean clearCart(int userId);
}