import numpy as np
import math

def main1(T):
    num1=int(T)
    Tend=0.01
    sk=0.99

    distances=[[ 0, 24, 17, 30, 11, 19, 18, 26, 26, 14],
               [24,  0, 20, 19, 23, 25, 26, 16, 24, 24],
               [17, 20,  0, 14, 13, 20, 30, 17, 22, 15],
               [30, 19, 14,  0, 26, 16, 21, 24, 28, 30],
               [11, 23, 13, 26,  0, 21, 13, 13, 16, 27],
               [19, 25, 20, 16, 21,  0, 16, 27, 15, 29],
               [18, 26, 30, 21, 13, 16,  0, 17, 14, 16],
               [26, 16, 17, 24, 13, 27, 17,  0, 23, 20],
               [26, 24, 22, 28, 16, 15, 14, 23,  0, 18],
               [14, 24, 15, 30, 27, 29, 16, 20, 18,  0]]


    _matrix=np.array(distances)
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

    while (num1>Tend):
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
            pa=math.exp(-de/num1)
            ra=np.random.uniform(0,1)
            if ra<pa:
                cozum=komsu.copy()
                obj=obj_komsu

        num1=num1*sk

        if obj<min(objit):
            objit.insert(iterasyon,obj)
        else:
            objit.insert(iterasyon, objit[iterasyon-1])

        if objit[iterasyon]<objeniyi:
            cozumeniyi=cozum.copy()
            objeniyi=obj

        iterasyon=iterasyon + 1

    return "en iyi çözüm: \n"+str(cozumeniyi)+"\n" +"en iyi obj: " +str(objeniyi)