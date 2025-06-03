# 🛒 Store - Sistema de Tienda en Java

**Desarrollado por:** Esteban Vásquez

Un sistema completo de tienda desarrollado en Java puro con interfaz gráfica Swing, que simula un e-commerce con funcionalidades de administrador y cliente, utilizando archivos JSON como base de datos.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Instalación](#-instalación)
- [Uso](#-uso)
- [Funcionalidades](#-funcionalidades)
- [Arquitectura](#-arquitectura)
- [Capturas de Pantalla](#-capturas-de-pantalla)
- [Contribución](#-contribución)
- [Licencia](#-licencia)

## 🚀 Características

- **Sistema de Autenticación**: Login diferenciado para administradores y usuarios
- **Gestión de Productos**: CRUD completo de productos
- **Carrito de Compras**: Funcionalidad completa de carrito con persistencia
- **Panel de Administración**: Gestión completa de inventario y ventas
- **Interfaz Moderna**: UI desarrollada con Swing y componentes personalizados
- **Base de Datos JSON**: Persistencia de datos utilizando archivos JSON
- **Arquitectura MVC**: Separación clara de responsabilidades

## 🛠 Tecnologías Utilizadas

- **Java 8+**: Lenguaje principal de desarrollo
- **Maven**: Gestión de dependencias y construcción del proyecto
- **Swing**: Framework para la interfaz gráfica
- **JSON**: Formato de almacenamiento de datos
- **Jackson/Gson**: Procesamiento de archivos JSON
- **JUnit**: Testing (si está implementado)

## 📁 Estructura del Proyecto

```
Store/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── store/
│       │           ├── App.java                          # Clase principal
│       │           ├── models/                           # Modelos de datos
│       │           │   ├── Producto.java
│       │           │   ├── ProductoCarrito.java
│       │           │   ├── Usuario.java
│       │           │   ├── Sale.java
│       │           │   ├── SaleItem.java
│       │           │   └── SaleStatus.java
│       │           ├── repository/                       # Capa de acceso a datos
│       │           │   ├── ProductoRepositorioJson.java
│       │           │   ├── UsuarioRepositorioJson.java
│       │           │   └── VentaRepository.java
│       │           ├── services/                         # Lógica de negocio
│       │           │   ├── ProductoServicioImpl.java
│       │           │   ├── UsuarioServicioImpl.java
│       │           │   ├── SaleServiceImpl.java
│       │           │   └── interfaces/
│       │           │       ├── IProductoServicio.java
│       │           │       ├── IUsuarioServicio.java
│       │           │       └── ISaleService.java
│       │           ├── utils/                           # Utilidades
│       │           │   ├── Colors.java
│       │           │   ├── Fonts.java
│       │           │   ├── ChangePassword.java
│       │           │   ├── PasswordUtils.java
│       │           │   ├── UsuarioRegistrador.java
│       │           │   ├── ValidationUtils.java
│       │           │   ├── DateUtils.java
│       │           │   ├── FileUtils.java
│       │           │   └── JsonUtils.java
│       │           └── view/                            # Interfaz gráfica
│       │               ├── adminview/                   # Vistas de administrador
│       │               │   ├── AdminView.java
│       │               │   ├── AdminContentManager.java
│       │               │   ├── AdminViewStyles.java
│       │               │   └── LogoutPanel.java
│       │               ├── userview/                    # Vistas de usuario
│       │               │   ├── UserView.java
│       │               │   ├── UserDashboard.java
│       │               │   ├── ShoppingPanel.java
│       │               │   ├── ProfilePanel.java
│       │               │   └── OrderHistoryPanel.java
│       │               ├── auth/                        # Autenticación
│       │               │   ├── Login.java
│       │               │   ├── Register.java
│       │               │   ├── RegisterActionsPanel.java
│       │               │   ├── AuthBaseFrame.java
│       │               │   ├── constants/
│       │               │   │   └── AuthConstants.java
│       │               │   └── controller/
│       │               │       ├── LoginController.java
│       │               │       └── RegisterController.java
│       │               ├── panels/                      # Paneles específicos
│       │               │   ├── BasePanel.java
│       │               │   ├── profile/
│       │               │   │   ├── UserProfileController.java
│       │               │   │   ├── UserProfilePanel.java
│       │               │   │   ├── ProfileFieldFactory.java
│       │               │   │   ├── UserProfileConstants.java
│       │               │   │   └── fields/
│       │               │   │       ├── ProfileActionsPanel.java
│       │               │   │       └── ProfileSectionPanel.java
│       │               │   ├── users/
│       │               │   │   ├── ProductosClientePanel.java
│       │               │   │   └── UserPurchasesPanel.java
│       │               │   └── admin/
│       │               │       ├── UsersPanel.java
│       │               │       ├── CrudPanel.java
│       │               │       ├── ProductosPanel.java
│       │               │       └── SalesPanel.java
│       │               └── components/                  # Componentes reutilizables
│       │                   ├── buttons/
│       │                   │   ├── CustomButton.java 
│       │                   │   └── NavButton.java  
│       │                   ├── cards/
│       │                   │   ├── BaseCard.java            
│       │                   │   ├── ProductCard.java            
│       │                   │   ├── ProductCardMouseHandler.java
│       │                   │   ├── spaces/
│       │                   │   │   ├── ProductGridPanel.java
│       │                   │   │   └── ProductSearchHeader.java
│       │                   │   └── constants/
│       │                   │       ├── ProductsCardConstants.java
│       │                   │       ├── SearchHeaderConstants.java
│       │                   │       ├── BaseCardConstants.java
│       │                   │       └── GridConstants.java
│       │                   ├── dialogs/
│       │                   │   ├── admin/          
│       │                   │   │   ├── BaseEntityFormDialog.java
│       │                   │   │   ├── ProductFormDialog.java
│       │                   │   │   ├── SaleDialog.java
│       │                   │   │   ├── StockDialog.java
│       │                   │   │   └── UserFormDialog.java
│       │                   │   ├── auth/         
│       │                   │   │   ├── LoginForm.java
│       │                   │   │   └── RegisterForm.java
│       │                   │   ├── user/    
│       │                   │   │   ├── BaseDetailsDialog.java
│       │                   │   │   ├── CarritoDialog.java
│       │                   │   │   ├── ProductDetailsDialog.java
│       │                   │   │   ├── QuantityInputDialog.java
│       │                   │   │   ├── ChangePasswordDialog.java
│       │                   │   │   └── FormStyler.java
│       │                   │   ├── constants/                 
│       │                   │   │   ├── FormDialogConstants.java
│       │                   │   │   ├── ProductDetailsConstants.java
│       │                   │   │   ├── ProductFormDialogConstants.java
│       │                   │   │   ├── StockDialogConstants.java
│       │                   │   │   └── UserFormDialogConstants.java
│       │                   │   ├── ConfirmDialog.java         
│       │                   │   ├── InfoDialog.java      
│       │                   │   └── ErrorDialog.java   
│       │                   ├── filters/              
│       │                   │   ├── AdminFilterPanel.java
│       │                   │   └── ProductFilterPanel.java
│       │                   ├── forauth/          
│       │                   │   ├── CampoEntrada.java
│       │                   │   └── PanelIzquierdoInicio.java
│       │                   ├── forms/                
│       │                   │   ├── CustomTextField.java
│       │                   │   ├── CustomComboBox.java
│       │                   │   └── CustomDatePicker.java
│       │                   ├── NavBar/             
│       │                   │   ├── LogoPanel.java
│       │                   │   ├── Navbar.java
│       │                   │   └── NavButtonsPanel.java
│       │                   ├── shared/            
│       │                   │   ├── FormInputComponents.java
│       │                   │   └── RadioButtonGroup.java
│       │                   ├── tables/            
│       │                   │   ├── CustomTable.java
│       │                   │   ├── TableStyler.java 
│       │                   │   ├── ProductTable.java
│       │                   │   └── SalesTable.java
│       │                   └── TitlePanel.java 
│       └── resources/                                   # Recursos del proyecto
│           ├── Img/                                     # Imágenes
│           │   ├── card.jpeg
│           │   └── store.jpeg
│           └── json/                                    # Archivos de datos JSON
│               ├── productos.json
│               ├── usuarios.json
│               └── sales.json
├── target/                                             # Archivos compilados (Maven)
├── pom.xml                                             # Configuración de Maven
├── README.md
└── .gitignore
```

## ⚙️ Instalación

### Requisitos Previos

- Java JDK 8 o superior
- Maven 3.6 o superior
- IDE recomendado: IntelliJ IDEA, Eclipse, o NetBeans

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/store-java.git
   cd store-java
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar el proyecto**
   ```bash
   mvn exec:java -Dexec.mainClass="com.store.App"
   ```

   O alternativamente:
   ```bash
   mvn clean package
   java -jar target/store-1.0-SNAPSHOT.jar
   ```

## 🎯 Uso

### Credenciales por Defecto

**Administrador:**
- Usuario: `root`
- Contraseña: `123`

**Cliente de Prueba:**
- Usuario: `esvca`
- Contraseña: `123`

### Funcionalidades Principales

#### Para Administradores:
1. Iniciar sesión con credenciales de administrador
2. Gestionar inventario de productos (Crear, Leer, Actualizar, Eliminar)
3. Ver reportes de ventas
4. Administrar usuarios registrados

#### Para Clientes:
1. Registrarse como nuevo usuario o iniciar sesión
2. Navegar catálogo de productos
3. Agregar productos al carrito
4. Realizar compras
5. Ver historial de pedidos

## 🔧 Funcionalidades

### 🛡️ Sistema de Autenticación
- Login seguro con validación de credenciales
- Registro de nuevos usuarios
- Diferentes roles (Admin/Cliente)
- Contraseña Hasheada

### 📦 Gestión de Productos
- Catálogo completo con categorías
- Búsqueda y filtrado
- Gestión de inventario

### 🛒 Carrito de Compras
- Agregar/eliminar productos
- Modificar cantidades
- Cálculo automático de totales

### 📊 Panel de Administración
- Dashboard con Datos
- Gestión completa de productos
- Reportes de ventas
- Administración de usuarios

### 💳 Sistema de Ventas
- Proceso de checkout completo
- Generación de facturas
- Historial de compras
- Tracking de pedidos

## 🏗️ Arquitectura

El proyecto sigue el patrón **MVC (Model-View-Controller)** con arquitectura en capas:

- **Models**: Entidades de datos (Producto, Usuario, Venta, etc.)
- **Repository**: Capa de acceso a datos (JSON)
- **Services**: Lógica de negocio e interfaces
- **View**: Interfaz gráfica con Swing
- **Utils**: Utilidades y helpers

### Patrones de Diseño Implementados

- **Repository Pattern**: Para el acceso a datos
- **Service Layer**: Para la lógica de negocio
- **Observer Pattern**: Para actualizaciones de UI
- **Factory Pattern**: Para creación de componentes
- **Singleton Pattern**: Para servicios únicos

## 📄 Base de Datos JSON

El sistema utiliza archivos JSON para simular una base de datos:

- `productos.json`: Información de productos
- `usuarios.json`: Datos de usuarios registrados
- `sales.json`: Historial de transacciones

### Ejemplo de Estructura JSON

```json
[
    {
      "id": 1,
      "nombre": "Laptop Gaming",
      "precio": 1299.99,
      "categoria": "Tecnología",
      "stock": 10,
      "descripcion": "Laptop para gaming de alta gama",
      "imagen": "laptop_gaming.jpg"
    }
]

```

## 🚀 Características Futuras

- [ ] Integración con base de datos real (MySQL/PostgreSQL)
- [ ] Sistema de notificaciones
- [ ] Múltiples métodos de pago
- [ ] Sistema de reviews y ratings
- [ ] API REST para servicios externos
- [ ] Aplicación móvil complementaria

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 🐛 Reportar Bugs

Si encuentras algún bug, por favor crea un issue con:
- Descripción detallada del problema
- Pasos para reproducir el error
- Screenshots si es necesario
- Información del sistema operativo y versión de Java

## 📜 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

⭐ **¡Si te gusta este proyecto, no olvides darle una estrella!** ⭐

---

*Desarrollado por Esteban Vásquez*