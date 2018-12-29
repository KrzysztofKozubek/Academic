<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <meta name="theme-color" content="#2196F3">
    <title>Baza Filmów by Krzysztof Kozubek</title><!-- CSS-->
    <link href="/stylesheets/plugin-min.css" type="text/css" rel="stylesheet">
    <link href="/stylesheets/custom-min.css" type="text/css" rel="stylesheet">
</head>
<body><!--Pre Loader-->
<div id="loader-wrapper">
    <div id="loader"></div>
    <div class="loader-section section-left"></div>
    <div class="loader-section section-right"></div>
</div><!--Navigation-->
<header>
    <div class="navbar-fixed">
        <nav id="nav_f" role="navigation" class="default_color">
            <section class="container">
                <dt class="nav-wrapper"><a id="logo-container" href="/" class="brand-logo">Baza Filmów</a>
                <ul class="right hide-on-med-and-down">
                    <li><a href="#contact">Kontakt</a></li>
                    <li><a href="/movies/">Filmy</a></li>
                    <li>
                        <form method="post" action="/movies/">
                            <ul>
                                <li><label for="search"><i class="material-icons">search</i></label></li>
                                <li><input id="search" name="search" type="search" required=""></li>
                            </ul>
                        </form>
                    </li>
                </ul>
                <ul id="nav-mobile" class="side-nav">
                    <li><a href="/">Strona główna</a></li>
                    <li><a href="/movies/">Filmy</a></li>
                    <li><a href="#contact">Kontakt</a></li>
                    <li>
                        <form method="post" action="/movies/">
                            <ul>
                                <li><label for="search"><i class="material-icons">search</i></label></li>
                                <li><input id="search" name="search" type="search" required=""></li>
                            </ul>
                        </form>
                    </li>
                </ul>
                <a href="#" data-activates="nav-mobile" class="button-collapse"><i
                        class="mdi-navigation-menu"></i></a></dt></section>
        </nav>
    </div>
</header><!-- Informaion-->
<div id="intro" class="section scrollspy">
    <section class="container">
        <dt class="row">
        <div class="col s12"><h2 class="center header text_h2">Znajdz film którego szukasz. Wyniki są ograniczone
                do<span class="span_h2"> 50</span> wyników.</h2></div>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown1" class="dropdown-content">
                    <li><a href="/movies/genre/Akcja/">Akcja<span class="badge">1076</span></a></li>
                    <li><a href="/movies/genre/Animacja/">Animacja<span class="badge">648</span></a></li>
                    <li><a href="/movies/genre/Baśń/">Baśń<span class="badge">63</span></a></li>
                    <li><a href="/movies/genre/Biograficzny/">Biograficzny<span class="badge">429</span></a></li>
                    <li><a href="/movies/genre/Dokumentalizowany/">Dokumentalizowany<span class="badge">21</span></a>
                    </li>
                    <li><a href="/movies/genre/Dokumentalny/">Dokumentalny<span class="badge">4860</span></a></li>
                    <li><a href="/movies/genre/Dramat/">Dramat<span class="badge">855</span></a></li>
                    <li><a href="/movies/genre/Dreszczowiec/">Dreszczowiec<span class="badge">12</span></a></li>
                    <li><a href="/movies/genre/Erotyczny/">Erotyczny<span class="badge">51</span></a></li>
                    <li><a href="/movies/genre/Etiuda/">Etiuda<span class="badge">20</span></a></li>
                    <li><a href="/movies/genre/Familijny/">Familijny<span class="badge">703</span></a></li>
                    <li><a href="/movies/genre/Fantasy/">Fantasy<span class="badge">647</span></a></li>
                    <li><a href="/movies/genre/Film-Noir/">Film-Noir<span class="badge">39</span></a></li>
                    <li><a href="/movies/genre/Gangsterski/">Gangsterski<span class="badge">45</span></a></li>
                    <li><a href="/movies/genre/Historyczny/">Historyczny<span class="badge">237</span></a></li>
                    <li><a href="/movies/genre/Horror/">Horror<span class="badge">1168</span></a></li>
                    <li><a href="/movies/genre/Karate/">Karate<span class="badge">14</span></a></li>
                    <li><a href="/movies/genre/Katastroficzny/">Katastroficzny<span class="badge">52</span></a></li>
                    <li><a href="/movies/genre/Komedia/">Komedia<span class="badge">3594</span></a></li>
                    <li><a href="/movies/genre/Kostiumowy/">Kostiumowy<span class="badge">135</span></a></li>
                    <li><a href="/movies/genre/Kr&amp;oacute;tkometrażowy/">Kr&amp;oacute;tkometrażowy<span
                                class="badge">689</span></a></li>
                    <li><a href="/movies/genre/Kryminał/">Kryminał<span class="badge">547</span></a></li>
                    <li><a href="/movies/genre/Melodramat/">Melodramat<span class="badge">195</span></a></li>
                    <li><a href="/movies/genre/Musical/">Musical<span class="badge">281</span></a></li>
                    <li><a href="/movies/genre/Muzyczny/">Muzyczny<span class="badge">528</span></a></li>
                    <li><a href="/movies/genre/Obyczajowy/">Obyczajowy<span class="badge">282</span></a></li>
                    <li><a href="/movies/genre/Przygodowy/">Przygodowy<span class="badge">510</span></a></li>
                    <li><a href="/movies/genre/Psychologiczny/">Psychologiczny<span class="badge">17</span></a></li>
                    <li><a href="/movies/genre/Romans/">Romans<span class="badge">879</span></a></li>
                    <li><a href="/movies/genre/Satyra/">Satyra<span class="badge">458</span></a></li>
                    <li><a href="/movies/genre/Sci-Fi/">Sci-Fi<span class="badge">207</span></a></li>
                    <li><a href="/movies/genre/Sensacyjny/">Sensacyjny<span class="badge">46</span></a></li>
                    <li><a href="/movies/genre/Sportowy/">Sportowy<span class="badge">1441</span></a></li>
                    <li><a href="/movies/genre/Surrealistyczny/">Surrealistyczny<span class="badge">141</span></a></li>
                    <li><a href="/movies/genre/Thriller/">Thriller<span class="badge">356</span></a></li>
                    <li><a href="/movies/genre/Western/">Western<span class="badge">374</span></a></li>
                    <li><a href="/movies/genre/Wojenny/">Wojenny<span class="badge">17</span></a></li>
                    <li><a href="/movies/genre/XXX/">XXX<span class="badge">7</span></a></li>
                </ul>
                <a href="#!" data-activates="dropdown1" class="btn dropdown-button">Kategoria<i class="right"></i></a>
            </section>
        </article>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown2" class="dropdown-content">
                    <li><a href="/movies/mark/0/">0 +</a></li>
                    <li><a href="/movies/mark/1/">1 +</a></li>
                    <li><a href="/movies/mark/2/">2 +</a></li>
                    <li><a href="/movies/mark/3/">3 +</a></li>
                    <li><a href="/movies/mark/4/">4 +</a></li>
                    <li><a href="/movies/mark/5/">5 +</a></li>
                    <li><a href="/movies/mark/6/">6 +</a></li>
                    <li><a href="/movies/mark/7/">7 +</a></li>
                    <li><a href="/movies/mark/8/">8 +</a></li>
                    <li><a href="/movies/mark/9/">9 +</a></li>
                </ul>
                <a href="#!" data-activates="dropdown2" class="btn dropdown-button">Ocena<i class="right"></i></a>
            </section>
        </article>
        <article class="col s12 m4 l4">
            <section class="center promo promo-example">
                <ul id="dropdown3" class="dropdown-content">
                    <li><a href="/movies/sort/id ASC/">id ASC</a></li>
                    <li><a href="/movies/sort/id DESC/">id DESC</a></li>
                    <li><a href="/movies/sort/title ASC/">title ASC</a></li>
                    <li><a href="/movies/sort/title DESC/">title DESC</a></li>
                    <li><a href="/movies/sort/releaseDate ASC/">releaseDate ASC</a></li>
                    <li><a href="/movies/sort/releaseDate DESC/">releaseDate DESC</a></li>
                </ul>
                <a href="#!" data-activates="dropdown3" class="btn dropdown-button">Sortuj po</a></section>
        </article>
        </dt></section>
</div><!-- Movie-->
<div id="work" class="section scrollspy">
    <section class="container"><h2 class="header text_b">Znalezione filmy</h2>
        <dt class="row"><!-- Movie-->
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/08/62/862/7517878.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Zielona mila / The Green Mile</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Zielona mila / The Green Mile<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Emerytowany strażnik więzienny opowiada przyjaci&amp;oacute;łce o niezwykłym mężczyźnie, kt&amp;oacute;rego
                            skazano na śmierć za zab&amp;oacute;jstwo dw&amp;oacute;ch 9-letnich dziewczynek.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Mar 24 2000 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/09/98/998/7314731.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Forrest Gump</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Forrest Gump<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Historia życia Forresta, chłopca o niskim ilorazie inteligencji z niedowładem kończyn, kt&amp;oacute;ry
                            staje się miliarderem i bohaterem wojny w Wietnamie.</p>
                        <p>Gatunek: Dramat Komedia</p>
                        <p>Premiera: Fri Nov 04 1994 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/10/48/1048/6925401.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Skazani na Shawshank / The Shawshank Redemption</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Skazani na Shawshank / The Shawshank Redemption<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Adaptacja opowiadania Stephena Kinga. Historia niesłusznie skazanego na dożywocie bankiera,
                            kt&amp;oacute;ry musi przeżyć w brutalnym świecie rządzonym przez strażnik&amp;oacute;w i
                            wsp&amp;oacute;łwięźni&amp;oacute;w.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Sun Apr 16 1995 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/06/28/628/7685907.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Matrix / The Matrix</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Matrix / The Matrix<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Haker komputerowy Neo dowiaduje się od tajemniczych rebeliant&amp;oacute;w, że świat, w kt&amp;oacute;rym
                            żyje, jest tylko obrazem przesyłanym do jego m&amp;oacute;zgu przez roboty.</p>
                        <p>Gatunek: Akcja Sci-Fi</p>
                        <p>Premiera: Fri Aug 13 1999 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/06/71/671/7016965.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Leon zawodowiec / L&amp;eacute;on</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Leon zawodowiec / L&amp;eacute;on<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Płatny morderca ratuje dwunastoletnią dziewczynkę, kt&amp;oacute;rej rodzina została zabita
                            przez skorumpowanych policjant&amp;oacute;w.</p>
                        <p>Gatunek: Dramat Kryminał</p>
                        <p>Premiera: Fri May 26 1995 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/95/09/9509/7579423.3.jpg" class="activator"></div>
                    <div class="card-content"><span
                            class="rolling card-title activator grey-text text-darken-4">Shrek</span></div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Shrek<i
                                class="mdi-navigation-close right"></i></span>
                        <p>By odzyskać sw&amp;oacute;j dom, brzydki ogr z gadatliwym osłem wyruszają uwolnić piękną
                            księżniczkę.</p>
                        <p>Gatunek: Animacja Familijny Komedia</p>
                        <p>Premiera: Fri Jul 13 2001 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/91/13/299113/7332755.3.jpg" class="activator"></div>
                    <div class="card-content"><span
                            class="rolling card-title activator grey-text text-darken-4">Avatar</span></div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Avatar<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Jake, sparaliżowany były komandos, otrzymuje misję i zostaje wysłany na planetę Pandora,
                            gdzie zaprzyjaźnia się z lokalną społecznością oraz postanawia jej pom&amp;oacute;c. &lt;br/&gt;</p>
                        <p>Gatunek: Sci-Fi</p>
                        <p>Premiera: Fri Dec 25 2009 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/09/36/936/7472818.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Gladiator</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Gladiator<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Generał Maximus - prawa ręka cesarza, szczęśliwy mąż i ojciec - w jednej chwili traci
                            wszystko. Jako niewolnik-gladiator musi walczyć na arenie o przeżycie.</p>
                        <p>Gatunek: Dramat historyczny</p>
                        <p>Premiera: Fri Jul 14 2000 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/01/87/187/7451731.3.jpg" class="activator"></div>
                    <div class="card-content"><span
                            class="rolling card-title activator grey-text text-darken-4">Titanic</span></div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Titanic<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Rok 1912, brytyjski statek Titanic wyrusza w sw&amp;oacute;j dziewiczy rejs do USA. Na
                            pokładzie emigrant Jack przypadkowo spotyka arystokratkę Rose. &lt;br/&gt;</p>
                        <p>Gatunek: Melodramat Katastroficzny</p>
                        <p>Premiera: Fri Apr 13 2012 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/72/11/487211/7258810.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Kac Vegas / The Hangover</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Kac Vegas / The Hangover<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Czterech przyjaci&amp;oacute;ł spędza wiecz&amp;oacute;r kawalerski w Las Vegas. Następnego
                            dnia okazuje się, że zgubili pana młodego i nic nie pamiętają.</p>
                        <p>Gatunek: Komedia</p>
                        <p>Premiera: Fri Aug 07 2009 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/73/09/37309/7515192.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Piraci z Karaib&amp;oacute;w: Klątwa Czarnej Perły / Pirates of the Caribbean: The Curse of the Black Pe</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Piraci z Karaib&amp;oacute;w: Klątwa Czarnej Perły / Pirates of the Caribbean: The Curse of the Black Pe<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Kowal Will Turner sprzymierza się z kapitanem Jackiem Sparrowem, by odzyskać swoją miłość -
                            porwaną c&amp;oacute;rkę gubernatora.</p>
                        <p>Gatunek: Fantasy Przygodowy</p>
                        <p>Premiera: Fri Sep 05 2003 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/91/36/9136/6908595.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Requiem dla snu / Requiem for a Dream</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Requiem dla snu / Requiem for a Dream<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Historia czw&amp;oacute;rki bohater&amp;oacute;w, dla kt&amp;oacute;rych używki są ucieczką
                            przed otaczającą ich rzeczywistością.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Mar 16 2001 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/10/65/1065/6900087.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Władca Pierścieni: Drużyna Pierścienia / The Lord of the Rings: The Fellowship of the Ring</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Władca Pierścieni: Drużyna Pierścienia / The Lord of the Rings: The Fellowship of the Ring<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Podr&amp;oacute;ż hobbita z Shire i jego ośmiu towarzyszy, kt&amp;oacute;rej celem jest
                            zniszczenie potężnego pierścienia pożądanego przez Czarnego Władcę - Saurona.</p>
                        <p>Gatunek: Fantasy Przygodowy</p>
                        <p>Premiera: Fri Feb 15 2002 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/11/63/1163/6936765.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Seksmisja</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Seksmisja<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Rok 2044, zostają odhibernowani dwaj ostatni przedstawiciele płci męskiej. Władza jest w
                            rękach Ligi Kobiet, kt&amp;oacute;ra nie zamierza z niej zrezygnować.</p>
                        <p>Gatunek: Komedia Sci-Fi</p>
                        <p>Premiera: Mon May 14 1984 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/08/25/825/6901015.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Sz&amp;oacute;sty zmysł / The Sixth Sense</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Sz&amp;oacute;sty zmysł / The Sixth Sense<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Psycholog dziecięcy pr&amp;oacute;buje pom&amp;oacute;c ośmioletniemu chłopcu widzącemu
                            zmarłych poradzić sobie z tym niezwykłym darem.</p>
                        <p>Gatunek: Thriller</p>
                        <p>Premiera: Fri Jan 14 2000 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/10/47/1047/7241863.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Milczenie owiec / The Silence of the Lambs</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Milczenie owiec / The Silence of the Lambs<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Seryjny morderca i inteligentna agentka łączą siły, by znaleźć przestępcę obdzierającego ze
                            sk&amp;oacute;ry swoje ofiary.</p>
                        <p>Gatunek: Thriller</p>
                        <p>Premiera: Tue Dec 31 1991 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/01/79/179/7710998.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Szeregowiec Ryan / Saving Private Ryan</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Szeregowiec Ryan / Saving Private Ryan<i
                                class="mdi-navigation-close right"></i></span>
                        <p>W poszukiwaniu zaginionego szeregowca wysłany zostaje doborowy oddział żołnierzy.</p>
                        <p>Gatunek: Dramat Wojenny</p>
                        <p>Premiera: Fri Sep 11 1998 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/82/96/468296/7239889.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Slumdog. Milioner z ulicy / Slumdog Millionaire</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Slumdog. Milioner z ulicy / Slumdog Millionaire<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Opowieść o młodym chłopaku, kt&amp;oacute;ry bierze udział w hinduskiej edycji &amp;quot;Milioner&amp;oacute;w&amp;quot;.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Feb 27 2009 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/46/13/124613/7612385.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Kod da Vinci / The Da Vinci Code</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Kod da Vinci / The Da Vinci Code<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Ekranizacja powieści Dana Browna. Tajemnicze morderstwo w Luwrze staje się kluczem do
                            rozwiązania jednej z największych zagadek strzeżonych przez tajemnicze stowarzyszenie.</p>
                        <p>Gatunek: Thriller</p>
                        <p>Premiera: Fri May 19 2006 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 6</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/14/70/1470/6917175.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Cast Away - poza światem / Cast Away</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Cast Away - poza światem / Cast Away<i
                                class="mdi-navigation-close right"></i></span>
                        <p>W wyniku katastrofy lotniczej inżynier firmy kurierskiej trafia na bezludną wyspę, gdzie musi
                            przeżyć.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Jan 19 2001 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/10/39/1039/7517880.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Pulp Fiction</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Pulp Fiction<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Przemoc i odkupienie w opowieści o dw&amp;oacute;ch płatnych mordercach pracujących na
                            zlecenie mafii, żonie gangstera, bokserze i parze okradającej ludzi w restauracji.</p>
                        <p>Gatunek: Gangsterski</p>
                        <p>Premiera: Fri Dec 15 2006 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/14/13/31413/6900422.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Dzień świra</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Dzień świra<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Adaś Miauczyński, 49-letni rozwiedziony polonista, żyje w kręgu swoich natręctw, nie
                            potrafiąc wyrwać się z nudy i rutyny dnia codziennego.</p>
                        <p>Gatunek: Dramat Komedia Psychologiczny</p>
                        <p>Premiera: Fri Jun 07 2002 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/08/37/837/7522091.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Podziemny krąg / Fight Club</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Podziemny krąg / Fight Club<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Dw&amp;oacute;ch mężczyzn znudzonych rutyną zakłada klub, w kt&amp;oacute;rym co tydzień
                            odbywają się walki na gołe pięści.</p>
                        <p>Gatunek: Thriller Psychologiczny</p>
                        <p>Premiera: Fri Feb 11 2000 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/22/25/32225/7519150.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Pianista / The Pianist</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Pianista / The Pianist<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Podczas drugiej wojny światowej Władysław Szpilman, znakomity polski pianista, stara się
                            przeżyć w okupowanej Warszawie. &lt;br/&gt;</p>
                        <p>Gatunek: Biograficzny Dramat Wojenny</p>
                        <p>Premiera: Fri May 24 2002 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/77/47/137747/7276639.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Bękarty wojny / Inglourious Basterds</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Bękarty wojny / Inglourious Basterds<i
                                class="mdi-navigation-close right"></i></span>
                        <p>W okupowanej przez nazist&amp;oacute;w Francji oddział złożony z Amerykan&amp;oacute;w
                            żydowskiego pochodzenia planuje zamach na Hitlera.</p>
                        <p>Gatunek: Wojenny</p>
                        <p>Premiera: Fri Sep 11 2009 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/07/02/702/6967799.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Siedem / Se7en</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Siedem / Se7en<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Dw&amp;oacute;ch policjant&amp;oacute;w stara się złapać seryjnego mordercę wybierającego
                            swoje ofiary wg specjalnego &amp;quot;klucza&amp;quot;.</p>
                        <p>Gatunek: Kryminał Thriller</p>
                        <p>Premiera: Thu Feb 15 1996 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/33/90/583390/7441162.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Nietykalni / Intouchables</span>
                    </div>
                    <div class="card-reveal"><span
                            class="card-title grey-text text-darken-4">Nietykalni / Intouchables<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Sparaliżowany milioner zatrudnia do opieki młodego chłopaka z przedmieścia, kt&amp;oacute;ry
                            właśnie wyszedł z więzienia.</p>
                        <p>Gatunek: Biograficzny Dramat Komedia</p>
                        <p>Premiera: Fri Apr 13 2012 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/08/91/500891/7354571.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Incepcja / Inception</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Incepcja / Inception<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Czasy, gdy technologia pozwala na wchodzenie w świat sn&amp;oacute;w. Złodziej Cobb ma za
                            zadanie wszczepić myśl do śpiącego umysłu.</p>
                        <p>Gatunek: Surrealistyczny Thriller Sci-Fi</p>
                        <p>Premiera: Thu May 07 2015 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/08/36/836/7531943.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">American Beauty</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">American Beauty<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Przechodzący kryzys wieku średniego mieszkaniec przedmieść Lester Burnham zakochuje się w
                            koleżance swojej nastoletniej c&amp;oacute;rki.</p>
                        <p>Gatunek: Dramat Komedia</p>
                        <p>Premiera: Fri Feb 11 2000 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/68/78/6878/7389475.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Kr&amp;oacute;l Lew / The Lion King</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Kr&amp;oacute;l Lew / The Lion King<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Targany niesłusznymi wyrzutami sumienia po śmierci ojca mały lew Simba skazuje się na
                            wygnanie rezygnując z przynależnego mu miejsca na czele stada.</p>
                        <p>Gatunek: Animacja Familijny</p>
                        <p>Premiera: Fri Aug 26 2011 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/42/56/464256/7317256.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Wyspa tajemnic / Shutter Island</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Wyspa tajemnic / Shutter Island<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Szeryf federalny Daniels stara się dowiedzieć, jakim sposobem z zamkniętej celi w pilnie
                            strzeżonym szpitalu dla chorych psychicznie przestępc&amp;oacute;w zniknęła pacjentka.</p>
                        <p>Gatunek: Dramat Thriller</p>
                        <p>Premiera: Fri Mar 26 2010 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/01/89/189/7544755.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Truman Show / The Truman Show</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Truman Show / The Truman Show<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Truman Burbank odkrywa, że jest gł&amp;oacute;wnym bohaterem reality show nadawanego 24
                            godziny na dobę.</p>
                        <p>Gatunek: Dramat Komedia Sci-Fi</p>
                        <p>Premiera: Fri Oct 23 1998 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/10/89/1089/7196615.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Ojciec chrzestny / The Godfather</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Ojciec chrzestny / The Godfather<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Opowieść o nowojorskiej rodzinie mafijnej. Starzejący się Don Corleone pragnie przekazać
                            władzę swojemu synowi.</p>
                        <p>Gatunek: Dramat Gangsterski</p>
                        <p>Premiera: Sun Dec 31 1972 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/02/52/40252/7535972.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Efekt motyla / The Butterfly Effect</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Efekt motyla / The Butterfly Effect<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Evan, kt&amp;oacute;ry potrafi podr&amp;oacute;żować w czasie, przekona się, że nawet
                            najdrobniejsza zmiana w przeszłości ma kolosalny wpływ na teraźniejszość.</p>
                        <p>Gatunek: Thriller Sci-Fi</p>
                        <p>Premiera: Fri Jun 18 2004 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/78/89/37889/7529602.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Bruce Wszechmogący / Bruce Almighty</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Bruce Wszechmogący / Bruce Almighty<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Reporter, kt&amp;oacute;ry narzekał nadmiernie na Boga, otrzymuje jego moc i przekona się, że
                            nie łatwo jest rządzić światem.</p>
                        <p>Gatunek: Fantasy Komedia</p>
                        <p>Premiera: Thu Aug 14 2003 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 6</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/63/51/236351/7198307.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Mroczny Rycerz / The Dark Knight</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Mroczny Rycerz / The Dark Knight<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Batman, z pomocą komisarza Gordona i prokuratora Harveya Denta, występuje przeciwko
                            przerażającemu i nieobliczalnemu Jokerowi, kt&amp;oacute;ry chce pogrążyć Gotham City w
                            chaosie.</p>
                        <p>Gatunek: Akcja Sci-Fi</p>
                        <p>Premiera: Fri Aug 08 2008 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/20/14/32014/7048673.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Epoka lodowcowa / Ice Age</span>
                    </div>
                    <div class="card-reveal"><span
                            class="card-title grey-text text-darken-4">Epoka lodowcowa / Ice Age<i
                                class="mdi-navigation-close right"></i></span>
                        <p>W czasie wielkiej migracji zwierząt tygrys szablozębny, mamut i leniwiec starają się oddać
                            ludzkie dziecko jego plemieniu.</p>
                        <p>Gatunek: Animacja Komedia Przygodowy</p>
                        <p>Premiera: Fri Jun 07 2002 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/18/41/11841/7494142.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Władca Pierścieni: Powr&amp;oacute;t kr&amp;oacute;la / The Lord of the Rings: The Return of the King</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Władca Pierścieni: Powr&amp;oacute;t kr&amp;oacute;la / The Lord of the Rings: The Return of the King<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Zwieńczenie filmowej trylogii wg powieści Tolkiena. Aragorn jednoczy siły Śr&amp;oacute;dziemia,
                            szykując się do bitwy, kt&amp;oacute;ra ma odwr&amp;oacute;cić uwagę Saurona od podążających
                            w kierunku G&amp;oacute;ry Przeznaczenia hobbit&amp;oacute;w.</p>
                        <p>Gatunek: Fantasy Przygodowy</p>
                        <p>Premiera: Thu Jan 01 2004 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/99/74/459974/7380825.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Siedem dusz / Seven Pounds</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Siedem dusz / Seven Pounds<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Mężczyzna przedstawiający się jako urzędnik podatkowy pojawia się w domach siedmiu
                            śmiertelnie chorych dłużnik&amp;oacute;w, by sprawdzić jakimi są ludźmi. Ma dla nich
                            niezwykły dar.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Mar 13 2009 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/33/83/123383/7232883.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Ciekawy przypadek Benjamina Buttona / The Curious Case of Benjamin Button</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Ciekawy przypadek Benjamina Buttona / The Curious Case of Benjamin Button<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Historia Benjamina Buttona, kt&amp;oacute;ry zamiast starzeć się - młodniał.</p>
                        <p>Gatunek: Fantasy Melodramat</p>
                        <p>Premiera: Fri May 22 2015 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/18/64/31864/7521208.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Piękny umysł / A Beautiful Mind</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Piękny umysł / A Beautiful Mind<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Biografia geniusza matematycznego Johna Nasha, kt&amp;oacute;ry został zatrudniony w agencji
                            wywiadu wojskowego, co wpłynęło na rozw&amp;oacute;j ciężkiej choroby psychicznej.</p>
                        <p>Gatunek: Biograficzny Dramat</p>
                        <p>Premiera: Fri Mar 01 2002 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/14/51/31451/6900425.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Władca Pierścieni: Dwie wieże / The Lord of the Rings: The Two Towers</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Władca Pierścieni: Dwie wieże / The Lord of the Rings: The Two Towers<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Drużyna Pierścienia zostaje rozbita, lecz zdesperowany Frodo za wszelką cenę chce wypełnić
                            powierzone mu zadanie. Aragorn z towarzyszami przygotowuje się, by odeprzeć atak hord
                            Sarumana.</p>
                        <p>Gatunek: Fantasy Przygodowy</p>
                        <p>Premiera: Fri Jan 31 2003 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/78/01/217801/7182225.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Jestem legendą / I Am Legend</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Jestem legendą / I Am Legend<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Tajemniczy wirus wymordował lub zamienił w krwiożercze bestie prawie całą ludzkość. Samotny
                            naukowiec Robert Neville poszukuje szczepionki, by odwr&amp;oacute;cić mutację.</p>
                        <p>Gatunek: Horror Sci-Fi</p>
                        <p>Premiera: Fri Jan 11 2008 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/89/31/108931/7705260.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Mr. &amp;amp; Mrs. Smith</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Mr. &amp;amp; Mrs. Smith<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Para płatnych zab&amp;oacute;jc&amp;oacute;w pracujących dla dw&amp;oacute;ch tajnych agencji
                            wiedzie monotonne małżeńskie życie. Niespodziewanie dostają zlecenie na siebie nawzajem.</p>
                        <p>Gatunek: Akcja</p>
                        <p>Premiera: Fri Aug 05 2005 00:00:00 GMT+0200 (Central European Daylight Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 6</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/60/62/426062/7423630.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Sherlock Holmes</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Sherlock Holmes<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Najsłynniejszy detektyw Sherlock Holmes z nieodłącznym przyjacielem dr. Watsonem szukają
                            sprawcy rytualnych morderstw.</p>
                        <p>Gatunek: Kryminał Przygodowy</p>
                        <p>Premiera: Fri Jan 15 2010 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/05/29/529/6900785.3.jpg" class="activator"></div>
                    <div class="card-content"><span
                            class="rolling card-title activator grey-text text-darken-4">Kiler</span></div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Kiler<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Jerzy Kiler, warszawski taks&amp;oacute;wkarz, przypadkowo zostaje wzięty za płatnego zab&amp;oacute;jcę
                            i umieszczony w areszcie. Wyciąga go stamtąd boss świata przestępczego, kt&amp;oacute;ry
                            oferuje mu nowe zadanie.</p>
                        <p>Gatunek: Komedia Sensacyjny</p>
                        <p>Premiera: Mon Nov 17 1997 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/53/55/5355/7640252.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Edward Nożycoręki / Edward Scissorhands</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Edward Nożycoręki / Edward Scissorhands<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Delikatny chłopak-cyborg, kt&amp;oacute;ry zamiast dłoni ma nożyce, nagle zostaje bez opieki
                            swojego tw&amp;oacute;rcy. W pobliskim miasteczku poznaje nastolatkę, a potem zakochuje się
                            w niej.</p>
                        <p>Gatunek: Dramat Fantasy</p>
                        <p>Premiera: Thu Dec 06 1990 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/65/80/476580/7239651.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Gran Torino</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Gran Torino<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Walt Kowalski to emerytowany weteran żyjący we własnym poukładanym świecie. Jego spok&amp;oacute;j
                            zostaje zburzony przez nowych sąsiad&amp;oacute;w z Azji, kt&amp;oacute;rych syn spr&amp;oacute;buje
                            ukraść mu ulubione auto.</p>
                        <p>Gatunek: Dramat</p>
                        <p>Premiera: Fri Mar 27 2009 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 8</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/67/21/6721/7595394.3.jpg" class="activator"></div>
                    <div class="card-content"><span class="rolling card-title activator grey-text text-darken-4">Kevin sam w domu / Home Alone</span>
                    </div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">Kevin sam w domu / Home Alone<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Przez świąteczny pośpiech ośmioletni Kevin zostaje sam w domu na Boże Narodzenie.</p>
                        <p>Gatunek: Familijny Komedia</p>
                        <p>Premiera: Fri Dec 25 1992 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section>
            <section class="col s12 m4 l4">
                <article class="card">
                    <div class="card-image waves-effect waves-block waves-light"><img
                            src="http://1.fwcdn.pl/po/35/97/163597/7521197.3.jpg" class="activator"></div>
                    <div class="card-content"><span
                            class="rolling card-title activator grey-text text-darken-4">300</span></div>
                    <div class="card-reveal"><span class="card-title grey-text text-darken-4">300<i
                                class="mdi-navigation-close right"></i></span>
                        <p>Ekranizacja komiksu Franka Millera, kt&amp;oacute;ra opowiada o bitwie pod Termopilami.</p>
                        <p>Gatunek: Dramat historyczny</p>
                        <p>Premiera: Fri Mar 23 2007 00:00:00 GMT+0100 (Central European Standard Time)</p>
                        <!--.getFullYear()}-#{item.releaseDate.getMonth()}-#{item.releaseDate.getDay()--><p>Ocena: 7</p>
                        <p>Dłygość: min</p></div>
                </article>
            </section><!-- END Movie--></dt>
    </section>
</div><!-- Parallax-->
<div class="parallax-container">
    <div class="parallax"><img src="/images/background_1.jpg"></div>
</div><!-- Footer-->
<footer id="contact" class="page-footer default_color scrollspy">
    <section class="container">
        <div class="col l6 s12">
            <form action="/" method="post" class="col s12">
                <dt class="row">
                    <article class="input-field col s6"><i
                            class="mdi-action-account-circle prefix white-text"></i><input id="icon_prefix"
                                                                                           name="InputName" type="text"
                                                                                           class="validate white-text"><label
                            for="icon_prefix" class="white-text">Podaj swoje imię</label></article>
                    <article class="input-field col s6"><i class="mdi-communication-email prefix white-text"></i><input
                            id="icon_email" name="InputEmail" type="email" class="validate white-text"><label
                            for="icon_email" class="white-text">Adres email</label></article>
                    <article class="input-field col s12"><i class="mdi-editor-mode-edit prefix white-text"></i><textarea
                            id="icon_prefix2" name="InputMessage"
                            class="materialize-textarea white-text"></textarea><label for="icon_prefix2"
                                                                                      class="white-text">Wiadomość</label>
                    </article>
                    <article class="col offset-s7 s5">
                        <button type="submit" class="btn waves-effect waves-light red darken-1">Wyślij<i
                                class="mdi-content-send right white-text"></i></button>
                    </article>
                </dt>
            </form>
        </div>
    </section>
    <section class="footer-copyright default_color">
        <div class="container">Made by<a href="http://krzysztof-kozubek.blogspot.com/" class="white-text">Krzysztof
                Kozubek</a>. Thanks to<a href="http://materializecss.com/" class="white-text"> materializecss</a></div>
    </section>
</footer><!-- Scripts-->
<script src="/javascripts/plugin-min.js"></script>
<script src="/javascripts/custom-min.js"></script>
</body>
</html>