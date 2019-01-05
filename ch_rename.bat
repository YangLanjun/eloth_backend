echo 正在批量重命名文件
set a=0
setlocal EnableDelayedExpansion
for %%n in (*.png) do (
set /A a+=1
echo a
if !a! equ 1 ren "%%n" "粉.png"
if !a! equ 2 ren "%%n" "紫.png"
if !a! equ 3 ren "%%n" "绿.png"
if !a! equ 4 ren "%%n" "蓝.png"
if !a! equ 5 ren "%%n" "黄.png"
if !a! equ 6 ren "%%n" "白.png"
if !a! equ 7 ren "%%n" "灰.png"
if !a! equ 8 ren "%%n" "黑.png"
if !a! equ 9 ren "%%n" "红.png"
)
pause