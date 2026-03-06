# crear datos de ejemplo: valores mensuales de una serie
valores <- c(120, 130, 125, 140, 135, 150, 160, 155, 170, 165, 180, 175)

# convertirlos en un objeto de serie temporal (frecuencia 12 = mensual)
ts_datos <- ts(valores, start = c(2020, 1), frequency = 12)

# mostrar los primeros valores
print(ts_datos)

# graficar la serie
plot(ts_datos, main = "Ejemplo de Serie de Tiempo",
     xlab = "Tiempo (meses)", ylab = "Valor",
     col = "blue", lwd = 2)