# Spring introduction
To repozytorium zawiera zestaw zadań, które pomogą Tobie zdobyć praktykę w korzystaniu z podstawowych, a zarazem niezbędnych konstrukcji wykorzystywanych w projektach opartych o framework Spring.

<h3 id="uruchomienie">Uruchomienie</h3>
Projekt korzysta z **java 21**. Aby skonfigurować używane JDK możesz skorzystać z [sdkman](https://sdkman.io/).

1. Aby zobaczyć dostępne dystrybucje użyj komendy: `sdk list java`.
2. Zainstaluj wybraną wersję java. *Przykładowe polecenie: `sdk install java 21.0.2-open`*.
3. Wskaż zainstalowaną wersję java. *Przykładowe polecenie: `sdk use java 21.0.2-open`*.

Zanim przejdziesz do rozwiązywania zadań upewnij się, że Twoje środowisko pracy jest poprawnie skonfigurowane. Aby to zrobić uruchom komendę:
```
./gradlew clean test
```
Jeżeli po jego wykonaniu zobaczysz napis **BUILD SUCCESSFUL** oznacza to, że wszystko jest ok.

### Zadania
Celem głównego branch’a repozytorium (`main`) jest potwierdzenie, że środowisko uruchomieniowe jest poprawnie skonfigurowane _(patrz "[Uruchomienie](#uruchomienie)")_.

Aby dostać się do zadań musisz przełączyć się na branch `Workshop_spring`. Użyj komendy:
```
git checkout Workshop_spring
```

Zadania znajdziesz w pliku `src/test/java/pl/umk/workshop/springintroduction/SpringIntroductionApplicationTests.java`.

Zawiera on przypadki testowe, które definiują zadania:
- Pojedynczy test realizuje jedno zadanie.
- Na początku testy będą kończyć się błędem (Nie przejmuj się, tak ma być 😃).
- Realizowanym celem jest sprawienie aby test zakończył się sukcesem.
- W komentarzu nad testem znajdziesz opis zadania oraz ewentualną podpowiedź.

Aby uruchomić **pojedynczy** test możesz użyć polecenia np:
```
./gradlew test --tests "pl.umk.workshop.springintroduction.SpringIntroductionApplicationTests.dependencyInjectionWithAnnotations"
```
Pamiętaj, aby przekazać odpowiednią ścieżkę do metody.

> W razie problemów rozwiązania znajdziesz na branchach `solution_{nazwa_zadania}` np: `solution_dependencyInjectionWithAnnotations`.

### Powodzenia!