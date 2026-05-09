CREATE OR REPLACE VIEW VW_ANALISIS_DEUDORES_PERIODO AS
SELECT 
    TO_CHAR(d.numrun, 'fm99G999G999') || '-' || d.dvrun AS "RUT_DEUDOR",
    INITCAP(d.pnombre) || ' ' || INITCAP(d.appaterno) || ' ' || INITCAP(d.apmaterno) AS "NOMBRE DEUDOR",
    COUNT(ct.nro_cuota) AS "TOTAL_CUOTAS",
    ROUND(AVG(ct.valor_cuota)) AS "PROMEDIO_VALOR_CUOTAS",
    TO_CHAR(MIN(ct.fecha_venc_cuota), 'DD-MM-YYYY') AS "FECHA_MAS_ANTIGUA",
    NVL(TO_CHAR(d.fono_contacto), 'Sin Información') AS "TELEFONO",
    UPPER(o.nombre_prof_ofic) AS "OCUPACION",
    td.cupo_disp_compra AS "CUPO_DISP_COMPRA"
FROM syn_deudor d
JOIN syn_ocupacion o ON d.cod_ocupacion = o.cod_ocupacion
JOIN syn_tarjeta_deudor td ON d.numrun = td.numrun
JOIN syn_cuota_tarjetas ct ON td.nro_tarjeta = ct.nro_tarjeta
WHERE UPPER(o.nombre_prof_ofic) NOT LIKE '%INGENIERO%'
  AND EXTRACT(YEAR FROM ct.fecha_venc_cuota) = (EXTRACT(YEAR FROM SYSDATE) - 1)
GROUP BY 
    d.numrun, 
    d.dvrun, 
    d.pnombre, 
    d.appaterno, 
    d.apmaterno, 
    d.fono_contacto, 
    o.nombre_prof_ofic, 
    td.cupo_disp_compra,
    td.nro_tarjeta
HAVING ROUND(AVG(ct.valor_cuota)) < (
    SELECT MAX(AVG(valor_cuota)) 
    FROM syn_cuota_tarjetas 
    GROUP BY nro_tarjeta
)
ORDER BY COUNT(ct.nro_cuota) ASC, td.cupo_disp_compra ASC;
GRANT SELECT ON VW_ANALISIS_DEUDORES_PERIODO TO PRY2205_EFT_CON;