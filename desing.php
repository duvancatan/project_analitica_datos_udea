<?php
// ejemplo.php - guarda y abre en tu servidor local (p. ej. XAMPP, PHP built-in)

$name = isset($_GET['name']) ? $_GET['name'] : 'mundo';
?>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <title>Ejemplo PHP</title>
</head>
<body>
  <h1>Hola, <?php echo htmlspecialchars($name, ENT_QUOTES, 'UTF-8'); ?>!</h1>
  <p>Fecha y hora del servidor: <?php echo date('Y-m-d H:i:s'); ?></p>

  <form method="get">
    <label>Nombre: <input name="name" /></label>
    <button type="submit">Enviar</button>
  </form>
</body>
</html>