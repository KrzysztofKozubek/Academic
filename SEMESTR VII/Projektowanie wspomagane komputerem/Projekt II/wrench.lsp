(defun C:wrench ()
  (setq L (getreal "\nPodaj wielkoœæ klucza: "))
  (setq PU (getpoint "\nPunkt pocz¹tkowy klucza: "))
  
  ;;Punkt pocz¹tkowy  
  (setq X0 (car PU))
  (setq Y0 (cadr PU))
  ;;Variables
  (setq Skale (/ L 10))
  (setq LHead (+ 2 0))
  (setq Angle1 (+ 0.78 0))
  (setq Angle2 (+ 0.52 0))
  (setq Angle3 (+ 1.04 0))
  (setq Angle4 (+ 2.09 0))

  
  ;;Pkt 1
  (setq Y1 ( + Y0 L))
  (setq PU1 (list X0 Y1))
  ;;Pkt 2
  (setq X2 ( + (* (cos Angle1) (* 2 Skale)) X0))
  (setq Y2 ( + (* (sin Angle1) (* 2 Skale)) Y1))
  (setq PU2 (list X2 Y2))
  ;;pkt 3
  (setq Y3 ( + Y2 (* LHead Skale)))
  (setq PU3 (list X2 Y3))
  ;;pkt 4
  (setq X4 ( - X2 Skale))
  (setq PU4 (list X4 Y3))
  ;;pkt 5
  (setq Y5 ( - Y3 (* LHead Skale)))
  (setq PU5 (list X4 Y5))
  ;;pkt 6
  (setq X6 ( - X4 (* (sin Angle4) Skale)))
  (setq Y6 ( - Y5 (* (- 0 (cos Angle4)) Skale)))
  (setq PU6 (list X6 Y6))
  ;;pkt 7
  (setq X7 ( - X6 Skale))
  (setq PU7 (list X7 Y6))
  ;;pkt 8
  (setq X8 ( - X7 (* (sin Angle4) Skale)))
  (setq Y8 ( - Y6 (* (cos Angle4) Skale)))
  (setq PU8 (list X8 Y8))
  ;;pkt 9
  (setq Y9 ( + Y8 (* LHead Skale)))
  (setq PU9 (list X8 Y9))
  ;;pkt 10
  (setq X10 ( - X8 Skale))
  (setq PU10 (list X10 Y9))
  ;;pkt 11
  (setq Y11 ( - Y9 (* LHead Skale)))
  (setq PU11 (list X10 Y11))
  ;;Pkt 12
  (setq X12 ( + (* (cos Angle1) (* 2 Skale)) X10))
  (setq Y12 ( + (* (- 0 (sin Angle1)) (* 2 Skale)) Y11))
  (setq PU12 (list X12 Y12))
  ;;Pkt 13
  (setq Y13 ( - Y12 L))
  (setq PU13 (list X12 Y13))
  ;;Pkt 14
  (setq X14 (/ (+ X0 X12) 2))
  (setq Y14 ( + (* (- 0 (sin Angle1)) Skale) Y13))
  (setq PU14 (list X14 Y14))
  
  
 
  (command "_PLINE" PU PU1 PU2 PU3 PU4 PU5 PU6 PU7 PU8 PU9 PU10 PU11 PU12 PU13 PU14 "_c")
)