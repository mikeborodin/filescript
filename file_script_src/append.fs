
 var fa.
 var fb.

 fa = file(dir+"/file_one.txt").
 fb = file(dir+"/file_two.txt").

 write(fa,"THIS IS FIRST FILE ").
 write(fa,"THIS IS SECOND FILE").

 out(read(fa)).
 out(read(fb)).

 append(fa,fb).
 out(read(fb)).
