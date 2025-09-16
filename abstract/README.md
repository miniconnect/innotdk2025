# HoloDB absztrakt (InnOtdk 2025)

(***általában előre adott, felgyülemlett adatok kezelésével foglalkozunk***)
(***néha azonban fordított a helyzet, és az adatokat kell odateremteni egy helyzetbe***)
(***egyszerű, de kicsit absztraktan hagyott példa erre***)

A jelen TDK kutatás azt vizsgálta, hogyan lehet többé-kevésbé valószerű szintetikus adatokat koherensen és hatékonyan rendelkezésre bocsátani azok előzetes legenerálása nélkül.
A javasolt megoldás egy teljesen virtuális relációs adattár, mely összetettebb lekérdezéseket is képes on-the-fly módon kiszolgálni.
A hatékony keresést és rendezést kriptográfiai módszerekre épülő virtuális indexekkel sikerült elérni.
A módosításokat számon tartó plusz réteg pedig írhatóvá alakítja az eredendően csak-olvasható adathalmazt.
A teljesítmény a leggyakoribb lekérdezéstípusok esetében összemérhető a hagyományos adatbázisokéval, ám generálási processzre és adattárhelyre egyáltalán nincs szükség.

A HoloDB prototípusból kinőtt termék,
mely egy általános célú adatbázis-szimuláló eszköz pillanatok alatt bocsát rendelkezésre egy teljes relációs adatbázist egy tömör, deklaratív konfiguráció alapján.
Java környezetben közvetlenül a JPA entitások alapján is indítható.
A konfiguráció módosításakor menet közben is képes magát újratölteni (live mode).
Különösen jól jöhet bemutatókhoz, a fejlesztés korai fázisában, prototipizáláskor, mockoláshoz, CI/CD folyamatokban, oktatásban, de akár hagyományos adatbázisok felpopulálásához is használható.
A HoloDB az elterjedt AI megoldásokkal ortogonálisnak tekinthető, ugyanakkor beilleszthető agent pipeline-okba, a konfiguráció pedig hatékonyan generálható nyelvi modellel.
A projekt teljes egészében nyílt forráskódú; a monetizálási terv SaaS szolgáltatásra és specializált integrációkra épül.
