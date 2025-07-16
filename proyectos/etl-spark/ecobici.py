from pyspark.sql import SparkSession
from pyspark.sql.functions import col, to_timestamp, hour, unix_timestamp
import matplotlib.pyplot as plt
import seaborn as sns
from pyspark.sql import functions as F # Convertir las columnas de fechas y horas a tipo timestamp

# Iniciar SparkSession
spark = SparkSession.builder.appName("AnalisisBicicletas").getOrCreate()

# Cargar el CSV en un DataFrame de Spark
file_path = '2024-11.csv'  # Actualiza esta ruta con la ubicación real del archivo
df = spark.read.csv(file_path, header=True, inferSchema=True)


# Convertir las columnas de fecha y hora a tipo string y luego concatenarlas correctamente
df = df.withColumn('Fecha_Retiro', 
                   F.to_timestamp(F.concat(F.col('Fecha_Retiro'), F.lit(' '), F.col('Hora_Retiro')), 'dd/MM/yyyy HH:mm:ss')) \
       .withColumn('Fecha_Arribo', 
                   F.to_timestamp(F.concat(F.col('Fecha_Arribo'), F.lit(' '), F.col('Hora_Arribo')), 'dd/MM/yyyy HH:mm:ss'))

# 1. Resumen estadístico de la edad
df.select('Edad_Usuario').describe().show()

# 2. Distribución de géneros
gender_counts = df.groupBy('Genero_Usuario').count().toPandas()
plt.figure(figsize=(6, 4))
sns.barplot(x='Genero_Usuario', y='count', data=gender_counts, palette='Set2')
plt.title('Distribución de Género de los Usuarios')
plt.xlabel('Género')
plt.ylabel('Número de Usuarios')
plt.show()

# 3. Bicicletas más utilizadas (Top 10)
top_bikes = df.groupBy('Bici').count().orderBy('count', ascending=False).limit(10).toPandas()
plt.figure(figsize=(10, 6))
sns.barplot(x='Bici', y='count', data=top_bikes, palette='Set1')
plt.title('Top 10 Bicicletas Más Utilizadas')
plt.xlabel('ID de Bicicleta')
plt.ylabel('Cantidad de Uso')
plt.xticks(rotation=45)
plt.show()

# Detener la sesión de Spark
spark.stop()
