echo ���������������ļ�
set a=0
setlocal EnableDelayedExpansion
for %%n in (*.png) do (
set /A a+=1
echo a
if !a! equ 1 ren "%%n" "��.png"
if !a! equ 2 ren "%%n" "��.png"
if !a! equ 3 ren "%%n" "��.png"
if !a! equ 4 ren "%%n" "��.png"
if !a! equ 5 ren "%%n" "��.png"
if !a! equ 6 ren "%%n" "��.png"
if !a! equ 7 ren "%%n" "��.png"
if !a! equ 8 ren "%%n" "��.png"
if !a! equ 9 ren "%%n" "��.png"
)
pause