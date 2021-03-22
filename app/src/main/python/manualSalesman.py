import numpy as np
import math

indeks=-1
boyut=0
float_formatter="{:.1f}".format
np.set_printoptions(formatter={'float_kind':float_formatter})
a=np.zeros((boyut,boyut),dtype=float)

def manual4():
    global indeks
    a=np.array([])
    tekrarlama=-1
    indeks=-1

    return "Matrix has been reset. Please enter a new matrix dimension."

def manual3(number1):

    global indeks
    indeks=indeks+1
    global boyut
    global a
    num1=float(number1)
    tekrarlama=indeks
    print(tekrarlama)

    if tekrarlama==0:
        boyut=int(num1)
        a=np.zeros((boyut,boyut))
        for i in range(boyut):
                    for j in range(boyut):
                        a[i][j]=-1
        return str(boyut)+" dimensional matrix is created."

    if 0<tekrarlama<((boyut**2)+1):
        sutun=(tekrarlama-1)%boyut
        kalansız=(tekrarlama-1)-sutun
        satir=kalansız/boyut
        satir1=int(satir)
        sutun1=int(sutun)
        a[satir1,sutun1]=num1

        return str(a)

def manual2():

    b=np.array(a)
    c=b.reshape(boyut,boyut)
    T=10000
    Tend=0.01
    sk=0.999
    _matrix=np.array(c)
    sehirsayisi=len(_matrix)
    liste=list(range(sehirsayisi))
    np.random.shuffle(liste)
    cozum=liste
    obj=0

    for i in range(sehirsayisi-1):
        sehir1=cozum[i]
        sehir2=cozum[i+1]
        obj=obj+_matrix[sehir1,sehir2]

    sehir1=cozum[len(cozum)-1]
    sehir2=cozum[0]
    obj=obj+_matrix[sehir1,sehir2]
    iterasyon=1
    objit=[]
    objit.insert(1,obj)
    cozumeniyi=cozum.copy()
    objeniyi=obj

    while (T>Tend):

        deg=list(range(sehirsayisi))
        np.random.shuffle(deg)
        deg1=deg[0]
        deg2=deg[1]
        komsu=cozum.copy()
        dummy=komsu[deg1]
        komsu[deg1]=komsu[deg2]
        komsu[deg2]=dummy
        obj_komsu=0

        for i in range(sehirsayisi-1):
            sehir1=komsu[i]
            sehir2=komsu[i+1]
            obj_komsu=obj_komsu+_matrix[sehir1,sehir2]

        sehir1=komsu[len(cozum)-1]
        sehir2=komsu[0]
        obj_komsu=obj_komsu+_matrix[sehir1,sehir2]

        if obj_komsu<obj:
            cozum=komsu.copy()
            obj=obj_komsu
        else:
            de=obj_komsu-obj
            pa=math.exp(-de/T)
            ra=np.random.uniform(0,1)
            if ra<pa:
                cozum=komsu.copy()
                obj=obj_komsu

        T=T*sk

        if obj<min(objit):
            objit.insert(iterasyon,obj)
        else:
            objit.insert(iterasyon, objit[iterasyon-1])

        if objit[iterasyon]<objeniyi:
            cozumeniyi=cozum.copy()
            objeniyi=obj

        iterasyon=iterasyon + 1

    return "Best solution: "+str(cozumeniyi)+"\n" +"Best value: " +str(objeniyi)
