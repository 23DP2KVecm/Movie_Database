# MovieInfoApp

**MovieInfoApp** ir vienkārša Java lietotne, kas ļauj meklēt filmas datus, izmantojot OMDB API. Lietotāji var ievadīt filmas nosaukumu, un programma atgriezīs informāciju par šo filmu, tostarp:

- Filmas nosaukums
- Gads
- Žanrs
- Režisors
- Aktieri
- Sižets
- Reitings

Lietotājs ievada filmas nosaukumu, un programma veic API pieprasījumu, lai iegūtu nepieciešamo informāciju. Pēc datu saņemšanas lietotāja interfeisā tiek parādīta detalizēta informācija par filmu trīs cilnēs:

- **Detalizētā informācija**: Filmas nosaukums, gads, žanrs, režisors un aktieri.
- **Sižets**: Filmas sižets.
- **Reitings**: Filmas reitingi (pagaidām tas ir fiksēts teksts, ka reitinga dati nav pieejami šajā versijā).

### Kā lietot

1. Ievadiet filmas nosaukumu meklēšanas laukā.
2. Klikšķiniet uz "Search" (Meklēt).
3. Lietotne atgriezīs filmas datus trīs atsevišķās cilnēs.

### Prasības

- Java 8 vai jaunāka versija.
- OMDB API atslēga (iepriekš konfigurējama, aizstājot 'YOUR_OMDB_API_KEY' ar īsto atslēgu).

### Instalēšana

1. Lejupielādējiet vai klonējiet šo repozitoriju.
2. Ievietojiet savu OMDB API atslēgu mainīgajā 'API_KEY' kodā.
3. Kompilējiet un palaidiet Java lietotni.