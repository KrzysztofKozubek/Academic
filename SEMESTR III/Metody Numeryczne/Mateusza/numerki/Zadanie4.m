tic
N=460;   % rozmiar macierzy - ulega zmianie podczas testow!
size=100; % ilosc macierzy w rodzinie 
odchylenie=1;
G=cell(size,1);
for i=1:size
    G{i,1}=normrnd(0,odchylenie,[N N]);
end
Gtrans=cellfun(@transpose,G,'UniformOutput',false);
iloczyn = cell (size,1);
for i=1:size
    iloczyn{i,1} = G{i,1}*Gtrans{i,1};  
end 
slad=cell(size,1);
for i=1:size
    slad{i,1} = trace(iloczyn{i,1});
end
Gnorm=cell(size,1);
for i=1:size
    Gnorm{i,1}=G{i,1}/sqrt(slad{i,1});
end
Gnt=cellfun(@transpose,Gnorm,'UniformOutput',false);
wartosci=cell(size,1);
for i=1:size
    wartosci{i,1}=eig(Gnorm{i,1}*Gnt{i,1});
end
minimum=cell(size,1);
for i=1:size
    minimum{i,1}=min(wartosci{i,1});
end
maximum=cell(size,1);
for i=1:size
    maximum{i,1}=max(wartosci{i,1});
end
Kappa=cell(i,1);
for i=1:size
    Kappa{i,1}=sqrt(maximum{i,1}/minimum{i,1});
end
s1=[minimum{:}];
srednia_min=sum(s1)/size;
s2=[maximum{:}];
srednia_max=sum(s2)/size;
s3=[Kappa{:}];
srednia_kappa=sum(s3)/size;
toc

