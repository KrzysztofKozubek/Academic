%%
%clear all
%clc
fun=inline('8-4.5*(x-sin(x))','x');
funder=inline('-4.5+4.5*cos(x)','x');
xest=2;err=0.0001;imax=20;
disp(' i       xest        err        fun(xest)  funder(xest)') 
%%
for i=1:imax
    xi=xest-fun(xest)/funder(xest);
    disp('----------------------------------------------------')
     fprintf('%3i %11.6f %11.6f %11.6f %11.6f\n',i,xest,err,fun(xest),funder(xest))
    if  abs((xi-xest)/xest)<err
    fprintf('\nSolution is %11.7f\n',xi)
        break
    end
    xest=xi;
end
%%
if i == imax
        fprintf('\nSolution not obtained %3i iterations.\n',imax)
        disp('Shabas');
    end