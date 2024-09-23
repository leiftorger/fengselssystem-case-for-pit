# Prosjektet er en besvarelse på case i forbindelse med intervju hos PIT den 20. august 2024
Prosjektet besvarer oppgave beskrevet i [tilsendt case](CASE.md).

# Oppstart og grensesnitt
* Tjenesten startes som en webserver
* Dokumentasjon av REST-grensesnitt er tilgjengelig via [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) 
* Frontend/GUI er basert på [Thymeleaf](https://www.thymeleaf.org/) og [Material components](https://github.com/material-components/material-components-web)

## Antagelser
* Det er valgt norsk som kodespråk da det antas at det er ønsket språk
* Det er valgt å kun bruke API-kall til ekstern tjeneste ved kjøring av unit-tester. Her kunne jeg tatt i bruk wiremock men det ble det ikke tid til.
* Det er ikke mye dokumentasjon på kodenivå. Mener at det er et poeng at koden er selv-forklarende i seg selv gjennom bruk av 
navn på klasser, metoder og parametere som stemmer med domenespråk. Vi snakker sikkert om dette i intervjuet også.
* Lombok er brukt for å unngå kokeplate-kode for toString, equals, hashcode og også builders ifm. populering av data ved testing
* Det er ikke sjekk på om fengslingstiden er løpt ut når fange løslates.