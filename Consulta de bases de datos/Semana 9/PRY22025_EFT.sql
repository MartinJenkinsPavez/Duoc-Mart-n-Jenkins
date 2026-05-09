--Sinonimos de las tablas, para poder utilizarlas o que las utilicen sin comprometer la seguridad, al no compartir los nombres reales de las tablas
CREATE PUBLIC SYNONYM syn_deudor FOR PRY2205_EFT.DEUDOR;
CREATE PUBLIC SYNONYM syn_ocupacion FOR PRY2205_EFT.OCUPACION;
CREATE PUBLIC SYNONYM syn_tarjeta_deudor FOR PRY2205_EFT.TARJETA_DEUDOR;
CREATE PUBLIC SYNONYM syn_cuota_tarjetas FOR PRY2205_EFT.CUOTA_TARJETAS;
CREATE PUBLIC SYNONYM syn_transaccion FOR PRY2205_EFT.TRANSACCION_TARJETA_DEUDOR;
CREATE PUBLIC SYNONYM syn_sucursal FOR PRY2205_EFT.SUCURSAL;
CREATE SYNONYM mi_analisis_tarjetas FOR PRY2205_EFT.T_ANALISIS_TARJETAS;

--Permisos a Desarrollador, para acceder a las respectivas tablas
GRANT SELECT ON DEUDOR TO PRY2205_EFT_DES;
GRANT SELECT ON OCUPACION TO PRY2205_EFT_DES;
GRANT SELECT ON TARJETA_DEUDOR TO PRY2205_EFT_DES;
GRANT SELECT ON CUOTA_TARJETAS TO PRY2205_EFT_DES;

--Permisos para que desarrollador pueda darle esos permisos a otros usuarios si lo desea
GRANT SELECT ON DEUDOR TO PRY2205_EFT_DES WITH GRANT OPTION;
GRANT SELECT ON OCUPACION TO PRY2205_EFT_DES WITH GRANT OPTION;
GRANT SELECT ON TARJETA_DEUDOR TO PRY2205_EFT_DES WITH GRANT OPTION;
GRANT SELECT ON CUOTA_TARJETAS TO PRY2205_EFT_DES WITH GRANT OPTION;

-- Permisos a roles D y C
GRANT SELECT ON TRANSACCION_TARJETA_DEUDOR TO PRY2205_ROL_D; -- Acceso de lectura a las tabla de transacciones, para que pueda consultar las tablas.
GRANT SELECT ON SUCURSAL TO PRY2205_ROL_D;
GRANT SELECT ON T_ANALISIS_TARJETAS TO PRY2205_ROL_C; -- Acceso a usar la tabla de analisis al ROl C que usará el usuario PRY2205_EFT_CON, ya que este puede usar la tabla resumen creada mas adelante


--Secuencia para darle un identificador a cada fila de la tabla T_ANALISIS_TARJETAS
CREATE SEQUENCE SEQ_T_ANALISIS 
START WITH 1 
INCREMENT BY 1;
--Ingreso de la secuencia a la tabla
INSERT INTO T_ANALISIS_TARJETAS (
    NUM_ANALISIS, 
    NRO_TARJETA, 
    TOTAL_CUOTAS, 
    MONTO_TOTAL_TRANSA, 
    FECHA_TRANSACCION, 
    DIRECCION, 
    MONTO_REAJUSTADO
)

SELECT                                                                          --Select que les asigna el identificador a cada uno de los datos que obtendrá el siguente select, para crear la tabla T_ANALISIS_TARJETAS
    SEQ_T_ANALISIS.NEXTVAL,
    datos_ordenados.nro_tarjeta,
    datos_ordenados.total_cuotas_transaccion,
    datos_ordenados.monto_total_transaccion,
    datos_ordenados.fecha_transaccion_fmt,
    datos_ordenados.direccion_fmt,
    datos_ordenados.monto_reajustado
FROM (
    SELECT                                                                   -- Select que busca, filtra por la letra A y ordena los datos para generar la tabla segun el requerimiento de monto total transaccion)
        t.nro_tarjeta,
        t.total_cuotas_transaccion,
        t.monto_total_transaccion,
        TO_CHAR(t.fecha_transaccion, 'DD/MM/YYYY') AS fecha_transaccion_fmt, --Convertir la fecha en formato legible
        INITCAP(s.direccion) AS direccion_fmt,                               --Convertir direccion (Comenzar con mayus y seguir con minus)
        CASE 
            WHEN t.monto_total_transaccion BETWEEN 200000 AND 300000 THEN ROUND(t.monto_total_transaccion * 1.05) --Condicion para multiplicar por 1.05. Considera también regla de redondeo
            WHEN t.monto_total_transaccion BETWEEN 300001 AND 500000 THEN ROUND(t.monto_total_transaccion * 1.07) --Condicion para multiplicar por 1.05
            ELSE t.monto_total_transaccion 
        END AS monto_reajustado                                                 -- Añadir cualquiera de los casos encontrados en montototaltransaccion a "monto reajustado"
    FROM syn_transaccion t
    JOIN syn_sucursal s ON t.id_sucursal = s.id_sucursal                    --Unir la tabla transacciones con tabla sucursales para obtener nombre/dirección de la sucursal (en lugar de solo id) y poder insertarlo en T_ANALISIS_TARJETAS
    WHERE UPPER(s.direccion) LIKE 'A%'                                      --Transformar todo a mayusculas para trabajar mas facil
      AND t.monto_total_transaccion >= 200000
    ORDER BY 
        t.nro_tarjeta ASC,                                                  --Ordenar los datos de forma ascendente
        CASE                                                                --Ordenar de forma descenedente si hizo varias transacciones distintas
            WHEN t.monto_total_transaccion BETWEEN 200000 AND 300000 THEN ROUND(t.monto_total_transaccion * 1.05) 
            WHEN t.monto_total_transaccion BETWEEN 300001 AND 500000 THEN ROUND(t.monto_total_transaccion * 1.07) 
            ELSE t.monto_total_transaccion
        END DESC
) datos_ordenados;
CREATE INDEX IDX_TRANSA_MONTO                                               --Creacion de indices para acceso mas rapido a datos especificos
ON TRANSACCION_TARJETA_DEUDOR (monto_total_transaccion);
COMMIT;                                                                     --Subir la tabla obtenida

SHOW USER;