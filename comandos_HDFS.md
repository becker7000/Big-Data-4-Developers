
# Comandos HDFS 

* ### Ver la versión de hadoop instalada
```shell
    hadoop version
```

* ### Verificamos los procesos de la JVM ejecutandose:
```shell
    jps
```

> ## Primer paso: crear la carpeta 'workspace' y abrir en cmd 

* ### Creando el primer directorio 
```shell
    hdfs dfs -mkdir /user
```

* ### Copiando un archivo de Windows a un directorio HDFS con sobreescritura

```shell
    hdfs dfs -put C:\Users\becke\Documents\CST\Big-Data-4-Developers\util\hola.txt /user/
```

* ### Ver los archivos de un directorio HDFS

```shell 
    hdfs dfs -ls /user/
```

* ### Ver el espacio libre del HDFS 

```shell
    hdfs dfs -df -h /
```

* ### Crear multiples directorios contenidos

```shell
    hdfs dfs -mkdir -p /user/2025/datos
```

---

## Demostración de -rmdir

Primer paso: (creamos un directorio de prueba)
```shell
    hdfs dfs -mkdir -p /user/prueba
```

Segundo paso: (le colocamos un archivo)
```shell
    hdfs dfs -put C:\Users\becke\Documents\CST\Big-Data-4-Developers\util\hola.txt /user/prueba
```

Tercer paso: (intentamos eliminarlo)

```shell
    hdfs dfs -rmdir /user/prueba
```
Nota: -rmdir intenta eliminar pero solo lo hace si está vacío

Cuarto paso: (revisamos los directorios)
```shell
    hdfs dfs -ls /user
    hdsf dfs -ls /user/prueba
```

Quinto paso: (eliminamos el archivo interno)
```shell
    hdfs dfs -rm /user/prueba/hola.txt
```

Sexto paso: (repetimos el comando -rmdir)
``` shell
    hdfs dfs -rmdir /user/prueba
```

Septimo paso: (revisamos el directorio /user)
```shell
    hdfs dfs -ls /user
```
Nota: notamos que ya no existe /user/prueba

---

* ### Copiando un archivo del directorio actual de Windows al HDFS

Nota: -put sobreescribe el archivo en caso de existir

```shell
    hdfs dfs -put archivo.txt /user/2025/datos
```

Revisamos:
```shell
    hdfs dfs -ls /user/2025/datos
```

* ### Copiando un archivo del directorio actual de Windows al HDFS

Nota: -copyFromLocal crea copias, sin importar si ya existe el archivo en el HDFS

```shell 
    hdfs dfs -copyFromLocal otro.txt /user/2025/datos
```

Revisamos otra vez:
```shell
    hdfs dfs -ls /user/2025/datos
```

* ### Ver los archivos de un directorio HDFS de forma recursiva

```shell
    hdfs dfs -ls -R /
```

* ### Descargar un archivo del HDFS en el directorio actual de Windows con alias -get

```shell
   cd descargas
   hdfs dfs -get /user/2025/datos/otro.txt archivo_local.txt
```

* ### Descargar un archivo del HDFS en el directorio actual de Windows sin alias -get

```shell
    hdfs dfs -copyToLocal /user/2025/datos/archivo.txt archivo_local_2.txt
```

* ### Renombrar un archivo del HDFS

```shell
    hdfs dfs -mv /user/2025/datos/otro.txt /user/2025/datos/nuevo.txt
```

* ### Verificamos el nuevo nombre (22)

```shell
    hdfs dfs -ls /user/2025/datos/nuevo.txt
```

* ### Eliminar un directorio y su contenido de forma recursiva

```shell
    hdfs dfs -rm -r /user/2025/datos
```

* ### Verficamos si aún existe el directorio

```shell
    hdfs dfs -ls /user/2025
```

---
# Demostración con lema_python.txt

* ### Aplicamos los comandos y copiamos el lema de python
```shel
    python
```
Se abre la consola interactiva:
```python
    import this
```
Copiamos el lema de Python en lema_python.txt

* ### Copiamos el archivo en HDFS
```shell
    hdfs dfs -copyFromLocal lema_python.txt /user/lema_python.txt
```

* ### Verificamos que ya exista el archivo
```shell
    hdfs dfs -ls /user
```

* ### Mostrar el espacio ocupado en disco (en HDFS, disk usage)
```shell
    hdfs dfs -du /user/lema_python.txt
```
Nota: el primer número es el tamaño tomando en cuenta la replicación y el segundo el tamaño real del archivo.

* ### Mostrar el contenido de un archivo

```shell
    hdfs dfs -cat /user/lema_python.txt
```

## Ejercicio:
Crear una copia del archivo lema_python.txt en otro directorio.
Una vez creada la copia cambiarle el nombre y finalmente eliminarlo.

## Demostración de copias internas y permisos

Hacer una carpeta de copias:
```shell
    hdfs dfs -mkdir /user/copias
```

Copiar un archivo a la carpeta:
```shell
    hdfs dfs -cp /user/lema_python.txt /user/copias/lema_python_copia.txt
```

Verificar que exista la copia:
```shell
    hdfs dfs -ls /user/copias
```

* ### Espacio y cantidad de archivos en un directorio
```shell
    hdfs dfs -count /user
```
Datos obtenidos de izquierda a derecha:
1. Cantidad de directorios
2. Cantidad de archivos
3. Cantidad de bytes ocupados
4. Ruta analizada

* ### Creando un archivo vacío dentro de un directorio
```shell
    hdfs dfs -touchz /user/archivo_vacio.txt
```

# Comando File System Check -fsck

Sirve para verificar la integridad de los archivos almacenados en HDFS. Es una herramienta de diagnóstico muy útil para administradores o desarrolladores que quieren asegurarse de que los archivos:

1. No están corruptos
2. Están completamente replicados
3. No tienen bloques faltantes
4. Están bien distribuidos entre los nodos

```shell
    hdfs fsck /user/lema_python.txt
```

* ## Checar las opciones generales que tiene el comando hdfs dfs

```shell
    hdfs dfs
```

## Para más información checar la documentación oficial de Hadoop 3.3.5

* ### [Documentación oficial Hadoop 3.3.5](https://hadoop.apache.org/docs/r3.3.5/hadoop-project-dist/hadoop-common/FileSystemShell.html)   