function apprx = newt(func,gss,ite,tol)
f = inline(func);
fp = inline(diff(sym(func)));
iter = ite;
for n=1:iter;
    oldgss = gss;
    newgss = gss - (f(gss)/fp(gss));
    gss = newgss;
end
if (abs(newgss-oldgss)) < tol
    apprx = gss;
    disp(iter);
else
    iter = ite+1;
    apprx = newt(func,gss,iter,tol);
end
end
