(defun random (/ modulus multiplier increment random)
  (if (not seed)
    (setq seed (getvar "DATE"))
  )
  (setq modulus    65536
        multiplier 25173
        increment  13849
        seed  (rem (+ (* multiplier seed) increment) modulus)
        random     (/ seed modulus)
  )
)


(defun C:magicSquare ()
  (setq N (getint "\nPodaj ilosc ³amanych: "))
  (setq C (getreal "\nPodaj kolor ³amanych: "))
  (setq B (getreal "\nPodaj grubosc ³amanych: "))
  (setq PU1 (getpoint "\nPunkt pocz¹tkowy prostok¹ta: "))
  (setq PU2 (getpoint "\nPunkt koñcowy prostok¹ta: "))


  (COMMAND "LAYER" "M" "0" "" "")
  
  
  ;;Punkt pocz¹tkowy  
  (setq X10 (car PU1))
  (setq Y10 (cadr PU1))
  ;;Punkt koñcowe  
  (setq X20 (car PU2))
  (setq Y20 (cadr PU2))

  ;;Prostokat
  ;;Pkt 1
  (setq PU1 (list X10 Y10))
  ;;Pkt 2
  (setq PU2 (list X20 Y10))
  ;;Pkt 3
  (setq PU3 (list X20 Y20))
  ;;Pkt 4
  (setq PU4 (list X10 Y20))
  
  (command "_PLINE" PU1 PU2 PU3 PU4 "_c")
  
  (setq Xtmp (+ (REM (random) X20) X10))
  (setq Ytmp (+ (REM (random) Y20) Y10))
  (setq PUtmp (list Xtmp Ytmp))
  
  (COMMAND "LAYER" "M" "LAYERCOMMAND" "C" "RED" "" "LW" "2" "" "")
  
;;Rysowanie losowych lini
  (repeat N
    	(setq X (+ (REM (* (random) X20 100) X20) X10))
	(setq Y (+ (REM (* (random) Y20 100) Y20) Y10))
    
	(setq PU (list X Y))
	(setq PUtmp (list Xtmp Ytmp))

	(command "_PLINE" PUtmp PU "_c")
    
	(setq Xtmp X)
	(setq Ytmp Y)
  )
 )