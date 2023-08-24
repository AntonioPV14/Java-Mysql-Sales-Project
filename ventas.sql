-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-08-2023 a las 03:49:55
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 5.6.39

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ventas`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `EliminarProducto` (IN `id_producto_param` INT)  BEGIN
    DELETE FROM productos
    WHERE id_producto = id_producto_param;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarCliente` (IN `nombre` VARCHAR(30), IN `apellido` VARCHAR(30), IN `ci` VARCHAR(15), IN `direccion` VARCHAR(100), IN `telefono` VARCHAR(15))  BEGIN
    INSERT INTO clientes (nombre, apellido, ci, direccion, telefono)
    VALUES (nombre, apellido, ci, direccion, telefono);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarProducto` (IN `descripcion` VARCHAR(200), IN `precio` DECIMAL(10,2), IN `stock` INT, IN `proveedor` VARCHAR(80), IN `categoria` VARCHAR(50))  BEGIN
    INSERT INTO productos (descripcion, precio, stock, proveedor, categoria)
    VALUES (descripcion, precio, stock, proveedor, categoria);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarProveedor` (IN `nombre_contacto` VARCHAR(80), IN `rif` VARCHAR(20), IN `direccion` VARCHAR(100), IN `ciudad` VARCHAR(50), IN `estado` VARCHAR(50), IN `telefono` VARCHAR(15))  BEGIN
    INSERT INTO proveedores (nombre_contacto, rif, direccion, ciudad, estado, telefono)
    VALUES (nombre_contacto, rif, direccion, ciudad, estado, telefono);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertarVentaConDetalles` (IN `cliente_ci` VARCHAR(15), IN `producto_vendido` VARCHAR(200), IN `cantidad` INT, IN `precio_unitario` DECIMAL(10,2))  BEGIN
    DECLARE venta_id INT;
    DECLARE producto_id INT;

    
    INSERT INTO ventas (id_cliente, fecha_venta, total_venta)
    VALUES ((SELECT id_cliente FROM clientes WHERE ci = cliente_ci), NOW(), cantidad * precio_unitario);

    
    SET venta_id = LAST_INSERT_ID();

    
    SELECT id_producto INTO producto_id
    FROM productos
    WHERE descripcion = producto_vendido;

    
    INSERT INTO detallesventa (id_venta, id_producto, cantidad, precio_unitario)
    VALUES (venta_id, producto_id, cantidad, precio_unitario);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarCliente` (IN `clienteID` INT(1), IN `nombre` VARCHAR(30), IN `apellido` VARCHAR(30), IN `ci` VARCHAR(15), IN `direccion` VARCHAR(100), IN `telefono` VARCHAR(15))  BEGIN
    UPDATE clientes
    SET nombre = nombre,
        apellido = apellido,
        ci = ci,
        direccion = direccion,
        telefono = telefono
    WHERE id_cliente = clienteID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarProducto` (IN `id_producto_param` INT, IN `descripcion_param` VARCHAR(200), IN `precio_param` DECIMAL(10,2), IN `stock_param` INT, IN `proveedor_param` VARCHAR(80), IN `categoria_param` VARCHAR(50))  BEGIN
    UPDATE productos
    SET
        descripcion = descripcion_param,
        precio = precio_param,
        stock = stock_param,
        proveedor = proveedor_param,
        categoria = categoria_param
    WHERE id_producto = id_producto_param;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ModificarProveedor` (IN `proveedorID` INT(11), IN `nombre_contacto` VARCHAR(80), IN `rif` VARCHAR(20), IN `direccion` VARCHAR(100), IN `ciudad` VARCHAR(50), IN `estado` VARCHAR(50), IN `telefono` VARCHAR(15))  BEGIN
    UPDATE proveedores
    SET nombre_contacto = nombre_contacto,
        rif = rif,
        direccion = direccion,
        ciudad = ciudad,
        estado = estado,
        telefono = telefono
    WHERE id_proveedor = proveedorID;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id_categoria` int(11) NOT NULL,
  `nombre_categoria` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `ci` varchar(15) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `clientes`
--
DELIMITER $$
CREATE TRIGGER `Cliente_Eliminado` AFTER DELETE ON `clientes` FOR EACH ROW BEGIN
    INSERT INTO papelera (tabla, accion, id_eliminado, nombre, apellido, ci, direccion, telefono, fecha_eliminacion)
    VALUES ('clientes', 'DELETE', OLD.id_cliente, OLD.nombre, OLD.apellido, OLD.ci, OLD.direccion, OLD.telefono, NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallesventa`
--

CREATE TABLE `detallesventa` (
  `id_detalleventa` int(11) NOT NULL,
  `id_venta` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `precio_unitario` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `detallesventa`
--
DELIMITER $$
CREATE TRIGGER `ActualizarStockVenta` AFTER INSERT ON `detallesventa` FOR EACH ROW BEGIN
    DECLARE productoID INT;
    DECLARE cantidadVenta INT;
    
    SET productoID = NEW.id_producto;
    SET cantidadVenta = NEW.cantidad;
    
    UPDATE productos
    SET stock = stock - cantidadVenta
    WHERE id_producto = productoID;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `Limite_Stock` BEFORE INSERT ON `detallesventa` FOR EACH ROW BEGIN
    DECLARE producto_stock INT;

    SELECT stock INTO producto_stock
    FROM productos
    WHERE id_producto = NEW.id_producto;

    IF NEW.cantidad > producto_stock THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cantidad a vender es mayor al stock disponible';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `papelera`
--

CREATE TABLE `papelera` (
  `id` int(11) NOT NULL,
  `tabla` varchar(50) NOT NULL,
  `accion` varchar(10) NOT NULL,
  `id_eliminado` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  `ci` varchar(15) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `fecha_eliminacion` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int(11) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `proveedor` varchar(80) DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id_proveedor` int(11) NOT NULL,
  `nombre_contacto` varchar(80) DEFAULT NULL,
  `rif` varchar(20) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `ciudad` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `proveedores`
--
DELIMITER $$
CREATE TRIGGER `Proveedor_Eliminado` AFTER DELETE ON `proveedores` FOR EACH ROW BEGIN
    INSERT INTO papelera (tabla, accion, id_eliminado, nombre_contacto, rif, direccion, ciudad, estado, telefono, fecha_eliminacion)
    VALUES ('proveedores', 'DELETE', OLD.id_proveedor, OLD.nombre_contacto, OLD.rif, OLD.direccion, OLD.ciudad, OLD.estado, OLD.telefono, NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `Id` int(11) NOT NULL,
  `NombreUsuario` varchar(40) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `CI` varchar(15) NOT NULL,
  `NomApe` varchar(50) NOT NULL,
  `Telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `ci_cliente` varchar(15) DEFAULT NULL,
  `fecha_venta` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_venta` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Disparadores `ventas`
--
DELIMITER $$
CREATE TRIGGER `EliminarVentaConDetalles` BEFORE DELETE ON `ventas` FOR EACH ROW BEGIN
    -- Eliminar los registros de detallesventa relacionados con la venta a eliminar
    DELETE FROM detallesventa WHERE id_venta = OLD.id_venta;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id_categoria`),
  ADD KEY `nombre_categoria` (`nombre_categoria`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `ci` (`ci`);

--
-- Indices de la tabla `detallesventa`
--
ALTER TABLE `detallesventa`
  ADD PRIMARY KEY (`id_detalleventa`),
  ADD KEY `id_venta` (`id_venta`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `papelera`
--
ALTER TABLE `papelera`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `categoria` (`categoria`),
  ADD KEY `proveedor` (`proveedor`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id_proveedor`),
  ADD UNIQUE KEY `nombre_contacto_2` (`nombre_contacto`),
  ADD KEY `nombre_contacto` (`nombre_contacto`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`Id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `ci_cliente` (`ci_cliente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detallesventa`
--
ALTER TABLE `detallesventa`
  MODIFY `id_detalleventa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `papelera`
--
ALTER TABLE `papelera`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id_proveedor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallesventa`
--
ALTER TABLE `detallesventa`
  ADD CONSTRAINT `detallesventa_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_venta`),
  ADD CONSTRAINT `detallesventa_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`categoria`) REFERENCES `categorias` (`nombre_categoria`),
  ADD CONSTRAINT `productos_ibfk_3` FOREIGN KEY (`proveedor`) REFERENCES `proveedores` (`nombre_contacto`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`ci_cliente`) REFERENCES `clientes` (`ci`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
