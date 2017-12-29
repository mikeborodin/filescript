var lines.

lines = dlines(file(dir)).

out("Total line count"+string(lines)).

lines = lines(file(dir+"/allcode1.fs")).

out("allcode1.fs line count"+string(lines)).


var text.
text = "My content".
var i.i=0.

while(i<100){
text=text+" lorem ipsum dolor sit amend".
i= i+1.
}

async(write(file(dir+"/bigfile.txt"),text)).
out("Writing file...").