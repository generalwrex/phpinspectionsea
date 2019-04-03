<?php

$y = [
    <error descr="The operation results to '(int)$x', the right operator can be omitted.">(int)$x</error> ?? '...',
    (<error descr="The operation results to '(string)$x', the right operator can be omitted.">(string)$x</error>) ?? '...',
    (<error descr="The operation results to '!$x', the right operator can be omitted.">!$x</error>) ?? '...',
    <error descr="Due to 'trim($x)' used as left operand, using '?:' instead of '??' would make more sense.">trim($x)</error> ?? '...',

    $x ?? <error descr="The operation results to '(int)$x', the right operator can be omitted.">(int)$x</error> ?? '...',
    $x ?? <error descr="Due to 'trim($x)' used as left operand, using '?:' instead of '??' would make more sense.">trim($x)</error> ?? '...',
];