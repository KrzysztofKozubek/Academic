(defun random (/ modulus multiplier increment random) (if (not seed) (setq seed (getvar "DATE")) ) (setq modulus    65536 multiplier 25173 increment  13849 seed  (rem (+ (* multiplier seed) increment) modulus) random     (/ seed modulus)))


(defun paint (N C B PU1 PU2)
  
  ;(setq N (getint "\nPodaj ilosc ³amanych: "))
  ;(setq C (getreal "\nPodaj kolor ³amanych: "))
  ;(setq B (getreal "\nPodaj grubosc ³amanych: "))
  ;(setq PU1 (getpoint "\nPunkt pocz¹tkowy prostok¹ta: "))
  ;(setq PU2 (getpoint "\nPunkt koñcowy prostok¹ta: "))

  (COMMAND "LAYER" "M" "0" "C" "white" "" "LW" "Default" "" "")
 
  ;;Punkt pocz¹tkowy  
  (setq X10 (car PU1)) (setq Y10 (cadr PU1))
  ;;Punkt koñcowe  
  (setq X20 (car PU2)) (setq Y20 (cadr PU2))

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
  
  (setq DX (- X20 X10 (* 2 B))) (setq DY (- Y20 Y10 (* 2 B)))
  
  (setq Xtmp (+ (REM (* (random) DX)) X10 B)) (setq Ytmp (+ (REM (* (random) DY)) Y10 B))
  (setq PUtmp (list Xtmp Ytmp))
  
  (COMMAND "LAYER" "M" "LAYERCOMMAND" "C" C "" "LW" B "" "")

;;Rysowanie losowych lini
  (repeat N
    (setq X (+ (REM (* (random) DX)) X10 B))
    (setq Y (+ (REM (* (random) DY)) Y10 B))
    (setq PU (list X Y))
    (setq PUtmp (list Xtmp Ytmp))
    (command "_PLINE" PUtmp PU "_c")
    (setq Xtmp X)
    (setq Ytmp Y)
  )

  (setq SU (strcat (rtos (fix X10)) "," (rtos (fix Y10))))
  (if (> (* 210 DX) (* 297 DY))
  (setq scale (strcat "247:" (rtos (+ (fix (abs DX)) 2))))   
  (setq scale (strcat "190:" (rtos (+ (fix (abs DY)) 2))))   
  )
  (if (> DX DY)
  (setq sizeFont (strcat (rtos (/ (abs DX) 10))))   
  (setq sizeFont (strcat (rtos (/ (abs DY) 10))))   
  )
  (alert SU)
  (command "TEXT" PU4 sizeFont "" "KKozubek" "")
  (command "_PLOT" "y" "" "DWG To PDF.pc3" "ISO A4 (210.00 x 297.00 MM)" "m" "l" "n" "l" scale SU "y" "acad.ctb" "y" "n" "n" "n" "" "n" "y" "Y" )
)


(defun C:MAGICSQUARE()

  (setq listC(list "RED" "GREEN" "BLUE" ))
  
  (if(not(setq dcl_id (load_dialog "Window.dcl")))
    (progn
      (alert "The DCL file could not be loaded.")
      (exit)
    )
    (progn
      (if (not (new_dialog "DCL_TEST" dcl_id))
        (progn
          (alert "The definition could not be found inside the DCL file")
          (exit)
        )
        (progn
          (action_tile "cancel" "(done_dialog 1)") (action_tile "accept" "(done_dialog 2)")

	  ;zmienne
	  (mode_tile "B" 3)
	  (action_tile "B" "(setq B $value)")
	  (mode_tile "X1" 3)
	  (action_tile "X1" "(setq X1 $value)")
	  (mode_tile "Y1" 3)
	  (action_tile "Y1" "(setq Y1 $value)")
	  (mode_tile "X2" 3)
	  (action_tile "X2" "(setq X2 $value)")
	  (mode_tile "Y2" 3)
	  (action_tile "Y2" "(setq Y2 $value)")
	  (mode_tile "C" 3)
	  (action_tile "C" "(setq C $value)")
	  (mode_tile "N" 3)
	  (action_tile "N" "(setq N $value)")
	  

          (setq ddiag(start_dialog)) 
          (unload_dialog dcl_id)

	  (setq PB (list (atoi X1) (atoi Y1)))
	  (setq PE (list (atoi X2) (atoi Y2)))
	  
          (if (= ddiag 1)
            (princ "\n \n ...DCL_TEST Cancelled. \n ")
          )
	  (if (= ddiag 2)
	     (paint (atoi N) C (atof B) PB PE)
          )
        )
      )
    )
  )
  (princ)
)