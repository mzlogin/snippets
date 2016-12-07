t=$(date +%s)
s=$((10#$t-3600*24*3))
sql=$(printf 'delete from user where updatetime<%d;' $s)
echo $sql > test.sql
mysql -u root -p111111 -Dtest < test.sql
