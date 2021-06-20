import numpy as np
import math

def roadFunction(distanceMatrix):

    T=1000
    num1=int(T)
    Tend=0.01
    sk=0.999

    distanceMatrixBoyut = len(distanceMatrix)
    distanceMatrixSplitBoyut = math.sqrt(distanceMatrixBoyut)
    distanceMatrixSplitBoyutInt = int(distanceMatrixSplitBoyut)

    newDistanceMatrix = np.array(distanceMatrix)
    distances = np.reshape(newDistanceMatrix,(distanceMatrixSplitBoyutInt,distanceMatrixSplitBoyutInt))

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

    return "Seçilen konumlar için en iyi rota:\n"+str(cozumeniyi)+"\n"+"\n"+str(cozumeniyi)+" rotası için hesaplanan mesafe: \n" +str(objeniyi/1000)+" km"