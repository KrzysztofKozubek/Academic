function root = secant(f,xo,x1,epsilon)
f = inline(f);
if (nargin < 3)
    error('Less input arguments');
elseif (nargin == 3)
    epsilon = 1e-6;
    Iter = 0;
    fprintf('xo = %d',xo);
    fprintf('\n');
    fprintf('f(xo) = %f',f(xo));
    fprintf('\n\n');
    disp('Iteration           x1           x2            f(x1)');
    disp('=========        ========     ========     ============');
    while(abs(f(x1)) >= epsilon)
        Iter = Iter + 1;
        if(f(x1)-f(xo) == 0)
            fprintf('f(x1)-f(xo) = 0 on iteration number %d',Iter);
            fprintf('\n');
            disp('We can not find the root');
            return;
        end
        x2 = x1 - (f(x1)*((x1-xo)/(f(x1)-f(xo))));
        fprintf('%3d',Iter);
        fprintf('%22.4f',x1);
        fprintf('%13.4f',x2);
        fprintf('%16.4f',f(x1));
        fprintf('\n');
        x1 = x2;
    end
    root = x2;
    
elseif(nargin == 4)
    Iter = 0;
    fprintf('xo = %d',xo);
    fprintf('\n');
    fprintf('f(xo) = %f',f(xo));
    fprintf('\n\n');
    disp('Iteration           x1           x2            f(x1)');
    disp('=========        ========     ========     ============');
    while(abs(f(x1)) >= epsilon)
        Iter = Iter + 1;
        if(f(x1)-f(xo) == 0)
            fprintf('f(x1)-f(xo) = 0 on iteration number %d',Iter);
            fprintf('\n');
            disp('We can not find the root');
            return;
        end
        x2 = x1 - (f(x1)*((x1-xo)/(f(x1)-f(xo))));
        fprintf('%3d',Iter);
        fprintf('%22.4f',x1);
        fprintf('%13.4f',x2);
        fprintf('%16.4f',f(x1));
        fprintf('\n');
        x1 = x2;
    end
    root = x2;
end