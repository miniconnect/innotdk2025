# HoloDB absztrakt (InnOtdk 2025)

- (*általában előre adott, felgyülemlett adatok kezelésével foglalkozunk*)
- (*néha azonban fordított a helyzet, és az adatokat kell odateremteni egy helyzetbe*)
- (*egyszerű, de kicsit absztraktan hagyott példa erre*)

- (*minek a megvalósíthatóságára kerestem a választ a kutatásban*)
- (*alapkoncepció*)
- (*kereshetőség és konzisztencia, kriptográfia*)
- (*számszerű és PoC eredmények*)

A kutatás közben fejlesztett prototípusból nőtte ki magát a HoloDB.
Ez az általános célú adatbázis-szimuláló eszköz pillanatok alatt bocsát rendelkezésre egy teljes relációs adatbázist egy tömör, deklaratív konfiguráció alapján.
Java környezetben közvetlenül a JPA entitások alapján is indítható.
A konfiguráció módosításakor menet közben is képes magát újratölteni (live mode).
(*nincs generálás, nincs tárhelyfoglalás*)
Különösen jól jöhet bemutatókhoz, a fejlesztés korai fázisában, prototipizáláskor, CI/CD folyamatokban, oktatásban,
de akár hagyományos adatbázisok felpopulálásához is használható.
(*a HoloDB paradigmikus és etikai vonatkozásai, zöld-vonal*)
A HoloDB az elterjedt AI megoldásokkal ortogonálisnak tekinthető, ugyanakkor beilleszthető agent pipeline-okba, a konfiguráció pedig hatékonyan generálható nyelvi modellel.
(* projekt jövője, nyílt forráskód, üzleti kilátások, SaaS*)
