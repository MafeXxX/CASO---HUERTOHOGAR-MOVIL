# 🌱 Huerto Hogar — E-commerce de productos frescos (Web)

Huerto Hogar es una tienda online enfocada en acercar productos del campo a familias en Chile, con énfasis en frescura, calidad y sostenibilidad. Incluye catálogo filtrable, carrito de compras, gestión de inventario, panel de administración y persistencia local para datos clave. Este repositorio corresponde a la **versión web (React)**.

---

## ✨ Características principales

- **Catálogo de productos** con imágenes, precio, stock, descripción y filtros por categoría.
- **Carrito de compras**: agregar/editar/eliminar, totales y validaciones de stock.
- **Pedidos (flujo front)**: confirmación visual (orden/boleta simulada) y estados básicos.
- **Panel de administración**: dashboard, usuarios, inventario, pedidos, reportes y categorías.
- **Inventario con ofertas**: marcar productos en oferta (%), recalcular precio y mostrar etiqueta.
- **Persistencia local (LocalStorage)** para catálogo extendido, stock global, ofertas, carrito, etc.
- **UI/UX**: paleta verde/mostaza/marrón, tipografías Montserrat/Playfair, diseño responsive.

---

## 🗂️ Estructura del proyecto

```text
huerto-hogar/
├─ public/
│  └─ favicon.ico
├─ src/
│  ├─ assets/
│  │  └─ img/                 # logos, banners, productos
│  ├─ components/
│  │  ├─ admin/               # AdminHeader, AdminSidebar, etc.
│  │  └─ ui/                  # Cards, Carruseles, Badges...
│  ├─ context/
│  │  └─ AuthContext.jsx
│  ├─ data/
│  │  └─ productos.json       # base del catálogo
│  ├─ pages/
│  │  ├─ Home.jsx
│  │  ├─ Catalogo.jsx
│  │  ├─ Detalle_producto.jsx
│  │  └─ admin/
│  │     ├─ AdminPerfil.jsx
│  │     ├─ AdminInventario.jsx
│  │     ├─ AdminUsuarios.jsx
│  │     ├─ AdminPedidos.jsx
│  │     ├─ AdminReportes.jsx
│  │     └─ AdminCategorias.jsx
│  ├─ routes/
│  │  └─ App.jsx               # enrutamiento principal
│  ├─ styles/
│  │  └─ admin/                # CSS del área admin
│  ├─ utils/
│  │  ├─ admin/                # helpers admin (stock, categorías, etc.)
│  │  └─ utils_Detalle_product/
│  └─ main.jsx
├─ package.json
└─ README.md
