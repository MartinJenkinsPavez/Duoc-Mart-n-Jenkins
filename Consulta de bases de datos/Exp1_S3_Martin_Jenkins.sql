SELECT 
    p.nro_propiedad AS "NRO. PROPIEDAD",
    p.direccion_propiedad AS "DIRECCIÓN",
    c.nombre_comuna AS "COMUNA",
    p.valor_arriendo AS "VALOR ARRIENDO",
    ROUND(p.valor_gasto_comun * 1.1) AS "VALOR GASTO COMÚN"
FROM propiedad p
JOIN comuna c ON p.id_comuna = c.id_comuna
WHERE p.valor_arriendo < &valor_maximo_arriendo AND p.valor_gasto_comun IS NOT NULL
  AND p.nro_dormitorios IS NOT NULL
  AND p.id_comuna IN (82, 84, 87)
ORDER BY c.nombre_comuna ASC, p.valor_arriendo DESC;


SELECT 
    p.nro_propiedad AS "NRO PROPIEDAD",
    TO_CHAR(ap.fecini_arriendo, 'DD/MM/YYYY') AS "FECHA INICIO",

    CASE 
        WHEN ap.fecter_arriendo IS NULL THEN 'Propiedad Actualmente Arrendada'
        ELSE TO_CHAR(ap.fecter_arriendo, 'DD/MM/YYYY') 
    END AS "FECHA TERMINO",

    TRUNC(NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) AS "DIAS ARRENDADA",

    TRUNC((NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) / 365) AS "AÑOS ARRENDADA",

    CASE 
        WHEN TRUNC((NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) / 365) >= 10 THEN 'COMPROMISO DE VENTA'
        WHEN TRUNC((NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) / 365) >= 5 
         AND TRUNC((NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) / 365) <= 9 THEN 'CLIENTE ANTIGUO'
        ELSE 'CLIENTE NUEVO'
    END AS "CLASIFICACION"

FROM propiedad p
JOIN arriendo_propiedad ap ON p.nro_propiedad = ap.nro_propiedad

WHERE TRUNC(NVL(ap.fecter_arriendo, SYSDATE) - ap.fecini_arriendo) >= &dias_minimos_arriendo
ORDER BY "DIAS ARRENDADA" DESC;


SELECT 
    tp.desc_tipo_propiedad AS "TIPO PROPIEDAD",
    COUNT(p.nro_propiedad) AS "TOTAL PROPIEDADES",
    ROUND(AVG(p.valor_arriendo)) AS "PROMEDIO ARRIENDO",
    ROUND(AVG(p.valor_gasto_comun)) AS "PROMEDIO GASTO COMUN"
FROM propiedad p
JOIN tipo_propiedad tp ON p.id_tipo_propiedad = tp.id_tipo_propiedad


GROUP BY tp.desc_tipo_propiedad


HAVING ROUND(AVG(p.valor_arriendo)) >= &promedio_minimo_arriendo

ORDER BY "PROMEDIO ARRIENDO" ASC;