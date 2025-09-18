# HoloDB absztrakt (InnOtdk 2025)

Az adatbázisokra általában úgy gondolunk, mint organikusan felgyülemlett adatok rendszerére, melyek feldolgozásra, interakcióra várnak.
A kutatás-fejlesztési projektek során azonban éppen fordított a helyzet: a feldolgozó környezet az, ami előre adott, és ehhez kell az adatokat szintetizálni.
Például egy szoftver fejlesztése, validálása majd bemutatója során szükséges egy megfelelő méretű, az elvárt sémához illeszkedő adathalmaz jelenléte, különben a rendszer tesztelése nem végezhető el megbízhatóan.

Kutatásom azt vizsgálta, hogyan lehetne valószerű szintetikus adatokat koherensen és hatékonyan rendelkezésre bocsátani azok előzetes legenerálása nélkül.
A javasolt megoldás egy teljesen virtuális relációs adattár, mely összetettebb lekérdezéseket is képes on-the-fly módon kiszolgálni.
A hatékony keresést és rendezést kriptográfiai módszerekre épülő virtuális indexekkel sikerült elérni.
A módosításokat számon tartó plusz réteg pedig írhatóvá alakítja az eredendően csak-olvasható adathalmazt.
A teljesítmény a leggyakoribb lekérdezéstípusok esetében összemérhető a hagyományos adatbázisokéval, ám generálási folyamatra és adattárhelyre egyáltalán nincs szükség.

A prototípusból kinőtt termék, a HoloDB, egy általános célú adatbázis-szimuláló eszköz,
mely pillanatok alatt bocsát rendelkezésre egy teljes relációs adatbázist egy tömör, deklaratív konfiguráció alapján.
Java környezetben közvetlenül a JPA entitások alapján is indítható.
A konfiguráció módosításakor menet közben is képes magát újratölteni (live mode).
Hasznos eszköz lehet kutatás-fejlesztési projektek bemutatójához, a fejlesztés korai fázisában, prototipizáláskor, mockoláshoz, CI/CD folyamatokban, oktatásban, de akár hagyományos adatbázisok felpopulálásához is használható.
A HoloDB az elterjedt MI megoldásokkal ortogonálisnak tekinthető, ugyanakkor beilleszthető agent pipeline-okba, a konfiguráció pedig hatékonyan generálható nyelvi modellel.
A projekt teljes egészében nyílt forráskódú; a monetizálási terv SaaS szolgáltatásra és specializált integrációkra épül.
