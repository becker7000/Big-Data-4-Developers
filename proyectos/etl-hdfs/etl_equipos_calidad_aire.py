import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from hdfs import InsecureClient

# Creamos un cliente no seguro (https sería seguro)
clienteHadoop = InsecureClient('http://localhost:9870')

# Leyendo desde HDFS 
# AQUÍ LE CAMBIAMOS EL NOMBRE A equipos_calidad_aire
with clienteHadoop.read('/user/datos_inecc/equipos_aire.csv') as file:
    df = pd.read_csv(file)

# 2. Sumar horizontalmente los equipos de las columnas especificadas
columnas_equipos = [
    "Equipo_automaticoO3", "Equipo_automaticoCO", "Equipo_automaticoSO2", "Equipo_automaticoNOx",
    "Equipo_automaticoPM10", "Equipo_automaticoPM2_5", "Equipo_automaticoPM10/PM2_5",
    "Equipo_automaticoPM10M", "Equipo_automaticoPM2_5M", "Equipo_manualO3",
    "Equipo_manualPM10M", "Equipo_manualPM2_5M", "MixtoO3", "MixtoCO", "MixtoSO2",
    "MixtoNOx", "MixtoPM10", "MixtoPM2_5", "MixtoPM10/PM2_5", "MixtoPM10M", "MixtoPM2_5M"
]

df["Total_equipos"] = df[columnas_equipos].sum(axis=1)

# 3. Agrupar por Entidad_federativa y sumar total de equipos
equipos_por_estado = df.groupby("Entidad_federativa")["Total_equipos"].sum().reset_index()

# 4. Ordenar por total de equipos
equipos_por_estado = equipos_por_estado.sort_values("Total_equipos", ascending=False)

# 5. Graficar con etiquetas al final de cada barra
plt.figure(figsize=(12, 6))
ax = sns.barplot(
    x='Total_equipos',
    y='Entidad_federativa',
    data=equipos_por_estado,
    palette='Set1'
)

# 6. Añadir etiquetas al final de cada barra
for i in ax.patches:
    plt.text(
        i.get_width() + 1,             # Posición x (ligeramente a la derecha)
        i.get_y() + i.get_height() / 2,  # Posición y (centro de la barra)
        int(i.get_width()),             # Texto (valor de la barra)
        va='center'
    )

# 7. Configurar la gráfica y mostrarla
plt.xlabel("Total de equipos")
plt.ylabel("Estado")
plt.grid(axis='x', linestyle='--')
plt.title("Total de equipos de medición de contaminantes por estado")
plt.tight_layout()
plt.show()
