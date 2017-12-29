var i.
var sizeOfArray.

arr myArray.
arr myStringArr.

push(myArray,1).
push(myArray,2).
push(myArray,3).

var strFromInput.


out("Calculating length of array: ").
sizeOfArray = size(myArray).

out("Iterating over array: "+string(sizeOfArray)).

i=0.
while(i<sizeOfArray){

    if(i%2==1){
        out(string(i) + " is odd").
    }
    out("Element of first array: "+string(get(myArray,i))).
    push(myStringArr,string(get(myArray,i))+" converted").
    create(file(dir+"/tmp_"+string(i)+".txt")).
    i = i+1.
}
