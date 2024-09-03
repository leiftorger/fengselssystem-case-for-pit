# Case: Fengsel
I denne oppgaven skal du lage et forenklet fengselssystem for kriminalomsorgen. Du kan
fritt velge hvilke rammeverk og bibliotek du vil bruke, men vi ønsker helst at du koder i Java
eller Kotlin. Det holder at løsningen skriver ut til konsollet. 
Løsning din bør bruke et byggverktøy og inneholde tester.

Viktig: Oppgaven har ingen fasit og brukes kun i vår dialog sammen i det tekniske intervjuet.
Det er lov å gjøre antagelser, men skriv om det i en readme-fil slik at du kan få uttelling.

## Oppgaven:
En fengselsbetjent tar imot nye straffedømte i fengselet og plasserer dem i ledige celler. De
er også ansvarlig for å løslate innsatte dersom forholdene for dette ligger til rette.
Fengselsbetjenter kan også vise nåværende kapasitet i en gitt celle og overføre fanger fra en
celle til en annen.
Løsningen din må være i stand til å laste inn dummy-data fra følgende API:
https://fengsel.bks-dev.politiet.no/fanger
Hvert objekt i json-array’et beskriver en fanges navn, alder, kjønn, cellenummer,
fengslingsdato og lengden på fengslingen. F.eks.:

```json
[
{
navn: "Navn Navnesen";
alder: "42";
kjonn: "Mann";
celleNummer: "101";
fengslingsDatoFra: "24-04-2002";
fengslingsDatoTil: "04-02-2042";
}
]
```
Endepunktet støtter kun GET, og returnerer dataene i tilfeldig rekkefølge.
Brukernavn er "api-user" og passord er "ODoVxkTZ6F1HBu0".

## Levering
Ta med laptopen hvor oppgaven er laget, så går vi igjennom den i intervjuet.

