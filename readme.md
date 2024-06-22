# 4IT353 - První semestrální úloha
# Humanity against Java

Tato aplikace je online implementace stolní hry “Cards against humanity” v Javě
s použitím socketu a JavaFX.

### Základní popis:

Klient aplikace má uživatelské rozhraní, kde hráči mohou vidět karty
které mají, karty na stole, aktivné "černé karty", a další herní informace.
Taktéž umožňuje hráčům interakci s hrou, včetně výběru karet a odesílání
odpovědí. Klient se připojí k serveru pomocí socketu a naváže spojení. Odesílá 
zprávy serveru (prostřednictvím GUI), jako je vybírání karet nebo zaslání
odpovědi na "černou kartu", a také přijímá zprávy od serveru, jako jsou
aktualizace herního stavu a nové karty.

Server řídí průběh hry, včetně toho, kdy se karty hrají a kdo vyhrál kolo.
Dále udržují stav hry, včetně seznamu hráčů, jejich karet a aktuálních
karet na stole. Po přijetí zprávy od klienta server reaguje podle pravidel
hry a aktualizuje stav hry. Server má “lobby”, které mohou hráče vytvořit a
připojovat se k ním.
Klienti a server komunikujou pomocí textových zpráv ve formátu json které jsou 
klientem nebo serverem serializovane/deserializováne z/do objektu, s
které vnitřní logika hry už pracuje.

### Průběh hry
Hráč se připojí k serveru zadáním ip adresy a portem, pak má zadat uživatelské
jméno. Potom má uživatel možnost vytvořit své lobby nebo se připojit k existujícímu. 
Kdy se nabere dostatečný počet účastníků, hra se automaticky startuje, 
dál průběh je stejný pro všechny účastníky. Hráčům se náhodně
vybere několik bílých kartíček (s textem pro vložení do černých kartiček) které
mají přehledné u sebe. Pak server začne první round ve kterém na
obrazovce se objeví černá karta s textem a mezerou. Účastníci mají
neomezený čas na vybrání nejvtipnější odpovědí, a až všichní účastniky vyberou 
svou kartu, všišechny kartičky budou viditelné pro všechny a muže každý hráč 
zvolit nejlepší odpověď. Odpověď hráče která nabrala největší počet hlasů vyhrává round a
hráčů se načítají body. A takto probíhají všichni roundy do konce hry. Na konci
vyhrává hráč s největším počtem bodů.

### Nedořešené problémy
- Hesla na lobby
- Timer na odpověd hráče
- Zákaz výberu své karty
- Sjednocení polling mechanizmu na straně serveru a klientu a taky mezi přikazy