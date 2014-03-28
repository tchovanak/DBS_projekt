-- 									SCENAR 1
-- Rozdiel sumárneho zisku z predaja produktov za dané obdobie 
-- voči stanovenému plánu v prehľade na regióny a pobočky.
-- hlavny select spoji dokopy najoinovanu tabulku s planom a vypocita rozdiel
SELECT zisky.region, zisky.pocet_predajov, zisky.suma_zisku, plan.suma_plan, (zisky.suma_zisku/plan.suma_plan) as plnenie_planu 
FROM (SELECT pred.id_produkt,reg.id as id_region,reg.nazov as region, count(*) as pocet_predajov, sum(pred.objem_eur) as suma_zisku
FROM 
	(SELECT * FROM predaj_produktu 
	WHERE datum >= '1/1/2014' AND datum <= '28/2/2014') as pred 
JOIN zamestnanec_zmluva zm ON pred.id_zamestnanec = zm.id 
JOIN vedenie_pobocky ved_pob ON zm.id = ved_pob.id_zamestnanec 
JOIN pobocka pob ON pob.id = ved_pob.id_pobocka 
JOIN region reg ON reg.id = pob.id_region 
GROUP BY reg.id,pred.id_produkt,reg.nazov 
HAVING pred.id_produkt = 3) 
as zisky 
LEFT JOIN (SELECT p.id_region,p.id_produkt,sum(p.plan_objem) as suma_plan FROM 
		(SELECT p1.id_region,p1.id_produkt,p1.plan_objem FROM plan_predaja p1
		 WHERE p1.od_datum >= '1/1/2014' AND p1.do_datum <= '28/2/2014') as p
	   GROUP BY p.id_region,p.id_produkt
           HAVING p.id_produkt = 3) as plan
ON plan.id_region = zisky.id_region AND plan.id_produkt = zisky.id_produkt



--                            SCENAR 2
--Zobrazenie prehľadu najúspešnejších predajcov v regiónoch 
--a tiež celkovej sumy ktoru predali.

WITH sub AS(
SELECT reg.id as region_id, reg.nazov as region,zm.id as zamestnanec_id,zm.prve_meno,zm.priezvisko,sum(pred.objem_eur) as zisk
FROM predaj_produktu pred
JOIN zamestnanec_zmluva zm ON pred.id_zamestnanec = zm.id
JOIN vedenie_pobocky ved_pob ON zm.id = ved_pob.id_zamestnanec
JOIN pobocka pob ON pob.id = ved_pob.id_pobocka
JOIN region reg ON reg.id = pob.id_region
GROUP BY reg.id,zm.id 
ORDER BY reg.nazov,zisk desc 
)
, sub2 AS(
SELECT sub.region_id, sub.region, max(zisk) as maximalny_zisk
FROM sub
GROUP BY sub.region,sub.region_id
)

SELECT sub.region,sub.prve_meno,sub.priezvisko, sub.zisk                  
FROM sub
JOIN sub2 ON sub.region_id = sub2.region_id
WHERE sub2.maximalny_zisk = sub.zisk AND sub2.region_id = sub.region_id 


-- 							SCENAR 3
--Zobrazenie koľko produktov na ktoré nie je vyškolený predal zamestnanec


SELECT count(*) FROM predaj_produktu pred
WHERE pred.datum > '1/1/2014' AND id_zamestnanec = 4 AND 
id_produkt NOT IN ( SELECT DISTINCT skol.id_produkt FROM skolenie_na_produkt skol
WHERE skol.id_zamestnanec_vyskoleny = 4 );




-- 							SCENAR 4
--Vyhľadanie klienta podľa zamestnanca, a produktu ktorý mu zamestnanec predal.

SELECT kl.prve_meno,kl.priezvisko
FROM klient kl
WHERE kl.id IN (SELECT pred.id_klient 
	FROM predaj_produktu pred
	WHERE pred.id_zamestnanec = (SELECT zm.id
		FROM zamestnanec_zmluva zm
		WHERE zm.prve_meno LIKE 'František' AND zm.priezvisko LIKE 'Chovaňák') AND pred.id_produkt = 1)