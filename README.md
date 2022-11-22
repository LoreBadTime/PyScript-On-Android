<h1> Template of Android application (Java) running along a PyScript core </h1>

<p> Tiny library/example of how to make pyscript work with native java apps <br>
For those wandering this implementation is completely hard coded, expect things to break, and for expert code readers expect to read crappy code :D <br>
Im not affiliated with the PyScript project <br>
PyScript repository here : https://github.com/pyscript/pyscript </p>

<h2>FAQ</h2>
<p> How to run PyScript library offline?<br></p>
<ol><li> Download the entire file from pyscript website https://pyscript.net/unstable/pyscript.js</li>
<li>Place it in src\main\assets </li>
<li><p> modify the src\main\assets\pyscript.html to use the new import<br>
from <script defer src="https://pyscript.net/unstable/pyscript.js"></script> <br>
to <script defer src="./pyscript.js"></script>

</p>
</li>


