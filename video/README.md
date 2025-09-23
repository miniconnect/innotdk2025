# Videó

## A videó szövege

### Vázlat 1

mottómondat

adatbázis, de nincsenek benne adatok
jó ehhez meg ahhoz

Képzelj el egy nagy szorzótáblát. Nyilvánvalóan bármely két szám szorzatát könnyen ki lehet olvasni belőle a megfelelő oszlop megfelelő sorában. Azonban aki tud szorozni, meg tudja mondani bármely mező tartalmát, anélkül, hogy szüksége lenne egy előregyártott táblázatra.

A HoloDB is hasonlóan működik, de összetett relációs struktúrákra és bizonyos típusú megkötésekre is működik. Egy kis kriptográfia bevetésével pedig képes hatékonyan de koherensen keresni a teljesen virtuális adatok között.

írható

...


... megadhatod egy helyi konfigurációs fájlban, JPA-entitásokon elhelyezett annotációkkal, vagy készülő felhőplatformunkon új adatbázisként.

QR-link

### Vázlat 2

(TODO: tömöríteni, fókuszálni)

Az adatok hagyományosan valamilyen begyűjtött és tárolt valamik, amik feldolgozásra várnak. Néha azonban fordított a helyzet, és egy helyzetnek van szüksége bizonyos jellegű adatokra, hogy működőképes legyen, ami gyakran mesterséges adatok tömegének a legenerálását és ideiglenes tárolását jelenti. Például játékok, szoftverfejlesztés, prototipizálás, oktatás, és így tovább. A HoloDB egy adatbázis-szimulátor, mely a szükséges adatok koherens halmazát előregenerálás nélkül azonnal rendelkezésre bocsátja, azokat tehát a háttérben sem tárolja. Bizonyos értelemben olyan, mint egy hologram.

Hogyan lehetséges ez? Képzeljünk el egy szorzótáblát. Ha nem tudunk szorozni, tárolnunk kell egy nagy táblát, hogy a szorzások eredményét meg tudjuk mondani. Jóval takarékosabb, ha ismerjük a szorzat kiszámításának algoritmusát. Ez bonyolultabb dolgokra is működik. Ráadásul egy kis kriptográfia bevetésével az adatok gyors kereshetősége is lehetővé válik.

A nyílt forrású HoloDB barátságos deklaratív konfigurációból indítva használható relációsadatbázis-szerverként, beágyazott adatbázisként. Akár közvetlenül ORM entitásokból közvetlenül is indíthatjuk. Számos beépített értékkiosztás-típus(...)

(TODO: Üzleti terv: SaaS platform és VIP adatgenerálók...)

(TODO: jövőkép, ökobarátság és AI-ortogonalitás hangsúlyozása)

### Vázlat 3

### A. Problémafelvetés

1. (8) Intró
  - kép: sötét + reflektor-sziluett, majd felgyullad + logó, majd alcím két lépésben "adatok" majd mellélökődve, azt egyenlővé nyomva: "újratöltve"
  - hang: intró-mottó (kb. "Nincs demó adatok(*adatbázis?*) nélkül, de az adatok (*...*) a HoloDB erre kínál megoldást"); figyelemmegragadás + hatásszünet
2. (10) Felvezetés
  - kép: én, az egyetem közelében állva
  - hang: rövid beköszönés ("Horváth Dávid vagyok, a HoloDB fejlesztője (*megalkotója*)", "a HoloDB (*erre és erre nyújt megoldást*)")
3. (7) David Attenborough style
  - kép: sötét: bújik a narrátor, valaki odébb gépel, zoom
  - hang: "éppen adatokat generál a demóhoz, már csak egy perc" ... "hopsz, rájött, hogy elgépelt egy beállítást"

### B. Megoldás

4. (12) Magyarázat (A LÉNYEG!)
  - kép: egy vagy két rövid, egyszerű rajzfilmes animáció, ami rátapint a lényegre, közben: on-the-fly (papírrepülő elrepülése a snittváltás)
  - hang: mi a HoloDB? egy-két mondatban, ... "on-the-fly generálja" --> kifutás: "nem akarsz adatgenerálással foglalkozni kipróbáláshoz"
5. (2) Intermezzo
  - kép: konfiguráció gépelése
  - hang: gépeléshang
6. (6) Hologram
  - kép: én, tovább magyarázok, majd Gábor Dénes dombormű + rajzfilmes overlay
  - hang: szöveg tovább, majd: "mint egy hologram"
7. (10) Kifejtés
  - kép: ismét a gépelés, AI + további feature-ök
  - hang: "ezt generálhatnád is" + rövid feature-körkép (vagy másképp beleolvadva)
8. (4) Takarékosság 1
  - kép: hatalmas adattár/szerverterem --> kis laptop, zoom
  - hang: néhány szó az adatméretről
9. (6) Takarékosság 2
  - kép: (váltás után) én, zöld környezetben távolról, végén innen zoom közelre
  - hang: madárcsicsergés, "zöldebb alternatíva" stb.

### C. Felhívás

10. (9) Meghívás (MÁSODIK LÉNYEG!, call-to-action)
  - kép: az előbbi snitt folytatása a zoom után, beszélek, sétálok, majd képernyőképre (github-oldal stb.), HoloDB QR, stb.
  - hang: meghívás a github-oldalra, meghívás kontributornak, felkérés szavazatra, megosztásra
11. (6) SQL
  - az intermezzo visszatér, pillanatsnittek, fájl mentés --> SQL írása, hatalmas OFFSET, majd: enter-nyomás, itt hirtelen blackout
12. (2) Zárókép
  - blackoutból átmenettel a záróképernyő: HoloDB dizájn újra (logo stb.), projekt és TDK-pályamunka adatai, OTDK (helyezés is!), QR-kód, a videó adatai stb.
  - hang: nincs

### Szövegterv

- Nincs demó adatok nélkül, de adatokat (*odateremteni*) nem egyszerű.
- Szia, én Horváth Dávid vagyok, a HoloDB szoftverprojekt kitalálója - és fejlesztője.
- Ez a kis eszköz leegyszerűsíti a demó adathalmazok kezelését, egyfajta mockolással.
- Ha már generáltál, seedeltél, populáltál adatokat fejlesztés közbeni kipróbáláshoz, bemutatáshoz, vagy bármi egyéb okból, tudod, mire gondolok.
- Ssss... épp adatokat generál a holnapi demóhoz... mindjárt kész... hopsz! rájött, hogy a (*...*) elgépelte...

- A HoloDB egy általános célú, nyílt forráskódú relációs adatbázis-szimulátor.
- Nem csak egy újabb fejlesztői eszköz, hanem egy új megközelítés.
- Nem tárol adatokat, nem foglal adattárhelyet, a lekéréseket on-the-fly szolgálja ki...
- ... tulajdonképpen olyan, mint egy hologram.
- Ahhoz, hogy ez hatékonyan működjön, rengeteg speciális alkatrésznek kell együttműködnie, a (*...*)-tól a (*...*)-n át a (*...*)-ig (*én már megalapoztam neked*).
- a deklaratív konfiguráció tömör, intuitív, és a YAML formátum ember és gép számára is barátságos; egy AI segédpilóta is könnyen (*beleszól*).
- Számos integráció és kiegészítő tool segíti az elindulást és a könnyű használatot.
- Válaszd a zöldebb alernatívát, szintetizálj gyorsabban, egyszerűbben és takarékosabban.

- További infókért látogass el a projekt github oldalára, próbáld ki működés közben.
- Ha van kedved, akár a fejlesztésbe is bekapcsolódhatsz.
- Ha arra érdemesnek találod, kérlek, támogass az InnOtdk-n szavazatoddal, illetve egy megosztással.
- Köszönöm hogy meghallgattál.
