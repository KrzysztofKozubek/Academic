/**
 * Created by Krzysztof on 2015-11-25.
 */

/**
 *
 * getStudentList([studenObj,studentObj,StudentObj,...]);
 * metoda przyjmuje na wejściu obiekty "studentObj", a na wyjściu zwraca tablicę (Array) zawierający identyczne obiekty z danymi studentów posortowany w kolejności alfabetycznej (od A do Z).
 *
 * getStudentListForCourse([studenObj,studentObj,StudentObj,...],"year","CourseName");
 * metoda przyjmuje na wejściu obiekty "studentObj", rok oraz nazwę kursu, a na wyjściu zwraca tablicę (Array) zawierającą obiekt z imieniem, nazwiskiem i numerem indeksu studenta posortowany w kolejności alfabetycznej (od A do Z).
 *
 * getAvarageForStudentInYear(studentObj,"year");
 * metoda przyjmuje na wejściu pojedyńczy obiekt "studentObj" oraz rok, na tej podstawie wylicza średnią ocen dla danego studenta ze wszystkich kursów w danym roku, zwracając na wyjściu liczbę zmiennoprzecinkową.
 *
 * getAvarageForStudentAllYears(studentObj);
 * metoda przyjmuje na wejściu pojedyńczy obiekt "studentObj", na tej podstawie wylicza średnią ocen dla danego studenta ze wszystkich kursów ze wszystkich lat studiów, zwracając na wyjściu liczbę zmiennoprzecinkową.
 *
 * getAverageForCourse([studenObj,studentObj,StudentObj,...],"year","CourseName");
 * metoda na wejściu przyjmuje obiekty "studentObj", rok oraz nazwę kursu, na tej podstawie wylicza średnią ocen dla danego kursu na podstawie ocen każdego studenta, na wyjściu zwracana jest liczba zmiennoprzecinkowa.
 *     Klasa powinna zawierać również metody pomocnicze:
 *
 * _sortByName() metoda sortująca alfabetycznie po nazwisku (od A do Z),
 * _getAverage() metoda licząca średnią ocen,
 * oraz inne metoday które będą używane kilkukrotnie.
 *
 */


function Helper() {
    //licz srednia
    this._getAverage = function (sum, amount) {
        average = sum / amount;
        return average.toPrecision(3);
    };
    //sortuj tablice po nazwisku
    this._sortByName = function (students) {
        for (var j = students.length - 1; j > 0; j--) {
            for (var i = 0; i < j; i++)
                if (students[i].last_name > students[i + 1].last_name) {
                    var tmp = students[i];
                    students[i] = students[i + 1];
                    students[i + 1] = tmp;
                }
        }
        return this._getName(students);
    };
    // wypisz nazwisko + imie dla obiektu w array
    this._getName = function (students) {
        var array = students.slice(0);
        for (var i = 0; i < students.length; i++) {
            array[i] = array[i].last_name + " " + array[i].first_name;
        }
        return array;
    };
    // wypisz liste studentow nazwiskami
    this.getStudentList = function (students) {
        return this._sortByName(students);
    };
    // licz srednia dla studenta we wszystkich latach
    this.getAvarageForStudentAllYears = function (student) {
        var sum = 0;
        var amount = 0;
        for (var year in student.courses) {
            for (var CourseName in student.courses[year]) {
                var array = student.courses[year][CourseName].grades.exercices;
                for (var i = 0; i < array.length; i++) {
                    sum = sum + array[i];
                    amount++;
                }
                var array = student.courses[year][CourseName].grades.lecture;
                for (var i = 0; i < array.length; i++) {
                    sum = sum + array[i];
                    amount++;
                }
            }
        }
        return this._getAverage(sum, amount);
    };
    // licz srednia dla studenta w danym roku
    this.getAvarageForStudentInYear = function (student, year) {
        var sum = 0;
        var amount = 0;
        for (var CourseName in student.courses[year]) {
            var array = student.courses[year][CourseName].grades.exercices;
            for (var i = 0; i < array.length; i++) {
                sum = sum + array[i];
                amount++;
            }
            var array = student.courses[year][CourseName].grades.lecture;
            for (var i = 0; i < array.length; i++) {
                sum = sum + array[i];
                amount++;
            }
        }
        return this._getAverage(sum, amount);
    };
    // licz srednia dla kursu
    this.getAverageForCourse = function (students, year, CourseName) {
        var sum = 0;
        var amount = 0;
        for (var student in students) {
            var courses = students[student].courses;
            var array = courses[year][CourseName].grades.exercices;
            for (var i = 0; i < array.length; i++) {
                sum = sum + array[i];
                amount++;
            }
            var array = courses[year][CourseName].grades.lecture;
            for (var i = 0; i < array.length; i++) {
                sum = sum + array[i];
                amount++;
            }
        }
        return this._getAverage(sum, amount);
    };


    // wypisz studentow danego kursu
    this.getStudentListForCourse = function (students, year, CourseName) {
        var array = new Array();
        var array2 = new Array();
        for (var i in students) {
            if (typeof(students[i].courses[year][CourseName]) !== "undefined") {
                array.push(students[i]);
            }
        }
        this._sortByName(array);
        for (var i in array) {
            var obj = {
                "first_name": (array[i].first_name).toString(),
                "last_name": (array[i].last_name).toString(),
                "album_number": (array[i].album_number).toString()
            };
            array2.push(obj);
        }
        return array2;
    };
    /*tutaj mozna oczywiscie napisac cos w stylu _getName, zeby mozna bylo potem leciec po tablicy i
     wypisywalo w kolejnosci alfabetycznej, ale dane ktore wrzucalem do <td> musialem wrzucac osobno
     ale zeby taka funkcja istniala jednak to:

     this._getNameForCourse = function (students){
     var array = students.slice(0);
     for(var i=0; i<students.length; i++){
     array[i] = array[i].last_name + " " + array[i].first_name + " " + array[i].album_number;
     }
     return array;
     };

     i wtedy w getStudentListForCourse wystarczy zeby bylo return this._getNameForCourse(array2);
     i działa
     tylko tak jak napisałem, łatwiej mi tak było wrzucic te dane do tabeli
     */
// End of Helper()
};


function ajax_get_json() {

    var hr = new XMLHttpRequest();
    hr.open("GET", "data.json", true);
    hr.setRequestHeader("Content-type", "application/json", true);
    hr.onreadystatechange = function asd() {
        if (hr.readyState == 4 && hr.status == 200) {

            var data = JSON.parse(hr.responseText);
            var gSL = document.getElementById("getStudentList");
            var gSLFC = document.getElementById("getStudentListForCourse");
            var gAFSIY = document.getElementById("getAvarageForStudentInYear");
            var gAFSAL = document.getElementById("getAvarageForStudentAllYears");
            var gAFC = document.getElementById("getAverageForCourse");

            var studentObj1 = data.s1;
            var studentObj2 = data.s2;
            var studentObj3 = data.s3;
            var studentObj4 = data.s4;

            var helper = new Helper();
            var studentList = [studentObj1, studentObj2, studentObj3, studentObj4];


            var tmp = helper.getStudentList(studentList);
            for (var i in tmp) {
                gSL.innerHTML += "<tr><td>" + tmp[i] + "</td></tr>";
            }


            var qwe = helper.getStudentListForCourse(studentList, "2013", "AlgorithmsI");
            for (var a in qwe) {
                gSLFC.innerHTML += "<tr><td>" + qwe[a].last_name + "</td><td>" + qwe[a].first_name + "</td><td>" + qwe[a].album_number + "</td></tr>";
            }


            gAFSIY.innerHTML += "<tr><td>" + studentObj3.last_name + "</td><td>" + studentObj3.first_name + "</td><td>" + helper.getAvarageForStudentInYear(studentObj3, "2013") + "</td></tr>";
            gAFSIY.innerHTML += "<tr><td>" + studentObj2.last_name + "</td><td>" + studentObj2.first_name + "</td><td>" + helper.getAvarageForStudentInYear(studentObj2, "2013") + "</td></tr>";
            gAFSIY.innerHTML += "<tr><td>" + studentObj4.last_name + "</td><td>" + studentObj4.first_name + "</td><td>" + helper.getAvarageForStudentInYear(studentObj4, "2013") + "</td></tr>";
            gAFSIY.innerHTML += "<tr><td>" + studentObj1.last_name + "</td><td>" + studentObj1.first_name + "</td><td>" + helper.getAvarageForStudentInYear(studentObj1, "2013") + "</td></tr>";


            gAFSAL.innerHTML += "<tr><td>" + studentObj3.last_name + "</td><td>" + studentObj3.first_name + "</td><td>" + helper.getAvarageForStudentAllYears(studentObj3, "2013") + "</td></tr>";
            gAFSAL.innerHTML += "<tr><td>" + studentObj2.last_name + "</td><td>" + studentObj2.first_name + "</td><td>" + helper.getAvarageForStudentAllYears(studentObj2, "2013") + "</td></tr>";
            gAFSAL.innerHTML += "<tr><td>" + studentObj4.last_name + "</td><td>" + studentObj4.first_name + "</td><td>" + helper.getAvarageForStudentAllYears(studentObj4, "2013") + "</td></tr>";
            gAFSAL.innerHTML += "<tr><td>" + studentObj1.last_name + "</td><td>" + studentObj1.first_name + "</td><td>" + helper.getAvarageForStudentAllYears(studentObj1, "2013") + "</td></tr>";


            gAFC.innerHTML += "<tr><td>" + "AlgorithmsI" + "</td><td>" + helper.getAverageForCourse(studentList, "2013", "AlgorithmsI") + "</td></tr>";
            gAFC.innerHTML += "<tr><td>" + "BasicPhysicsI" + "</td><td>" + helper.getAverageForCourse(studentList, "2013", "BasicPhysicsI") + "</td></tr>";
            gAFC.innerHTML += "<tr><td>" + "ProgrammingI" + "</td><td>" + helper.getAverageForCourse(studentList, "2013", "ProgrammingI") + "</td></tr>";
        }

    }
    hr.send(null);
}