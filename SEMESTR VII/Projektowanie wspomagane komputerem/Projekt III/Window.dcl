DCL_TEST : dialog {
  label = "Window by Kris Kozubek";
  : column {
    : boxed_row {   
      label = "Wspolrzedne prostokata";   
      : boxed_row {
        label = "Punkty poczatkowe";
       : edit_box { 
          key = "X1";
          value = "";
        }         
        : edit_box { 
          key = "Y1";
          value = "";
        }
      }
      : boxed_row {
        label = "Punkty koncowe";              
        : edit_box { 
          key = "X2";
          value = "";
        }         
        : edit_box { 
          key = "Y2";
          value = "";
        }
      }
    }
	: boxed_row {
	label = "Parametry lamanych";    
	  : boxed_row {
		label = "Grubosc lamanych";              
		: edit_box { 
		  key = "B";
		  value = "";
		}  
	  }
	  : boxed_row {
		label = "Kolor lamanych";  
		: edit_box { 
		  key = "C";
		  value = "";
		}
		}
		
	  : boxed_row {
		label = "Ilosc lamanych";  
		: edit_box { 
		  key = "N";
		  value = "";
		}
		}
	  }
	  
    ok_cancel; 
  }
  
}