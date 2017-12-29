var i.


for(i=0. i<10. i=i+1.){
    out("TEST 1" + string(i)).
    out("TEST 2" + string(i)).
}

thread({
    out("HELO" + string(123)).
    out("ALLO" + string(3453)).
}).


var fa.
var fb.

fa = file(dir+"/file_one.txt").
fb = file(dir+"/file_two.txt").
remove(fa).
remove(fb).


write(fa,"THIS IS FIRST FILE ").
write(fb,"THIS IS SECOND FILE").


append(fa,fb).
out(read(fb)).

run("dolphin .").
