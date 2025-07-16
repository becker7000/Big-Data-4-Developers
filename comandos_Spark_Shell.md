# Comandos Spark Shell

* ### Iniciar Spark Shell
```shell
    spark-shell
```

> Inicia una sesión interactiva de Spark en Scala.

---

* ### Crear un RDD desde una colección
```scala
    val numeros = sc.parallelize(1 to 10)
    numeros.collect()
```

---

* ### Filtrar números pares
```scala
    val pares = numeros.filter(_ % 2 == 0)
    pares.collect()
```

---

* ### Calcular cuadrados de los números
```scala
    val cuadrados = numeros.map(x => x * x)
    cuadrados.collect()
```

---

* ### Reducir (sumar todos los elementos)
```scala
    val suma = numeros.reduce(_ + _)
    println(s"Suma: $suma")
```

---

* ### Leer archivo de texto
Aplicar con el Zen de Python
```scala
    val texto = sc.textFile("file:///C:/ruta/del/archivo.txt")
    texto.take(5)
``` 

---

* ### Conteo de palabras (WordCount)
```scala
    val palabras = texto.flatMap(linea => linea.split(" "))
    val paresPalabra = palabras.map(p => (p, 1))
    val conteo = paresPalabra.reduceByKey(_ + _)
    conteo.collect()
```

---

* ### Guardar resultado en HDFS/local
```scala
    conteo.saveAsTextFile("file:///C:/Users/becke/Documents/CST/Big-Data-4-Developers/master/salida")
```

* ### Leer un CSV como DataFrame

```scala
    val df = spark.read.option("header", "true").csv("file:///C:/ruta/datos.csv")
    df.show()
```
Posteriormente aumentar el tamaño de la salida en HDFS:
hadoop-site.xml o core-site.xml
<property>
  <name>ipc.maximum.response.length</name>
  <value>134217728</value> <!-- 128MB (puedes aumentarlo aún más si es necesario) -->
</property>
hdfs:///localhost:9870/user/analisis/salida

---

* ### Imprimir esquema del DataFrame
```scala
    df.printSchema()
```

---

* ### Seleccionar columnas y agrupar
```scala
    df.select("columna1", "columna2").show()
    df.groupBy("columna1").count().show()
```

---


---

* ### Salir de spark-shell
```scala
    :quit
```
