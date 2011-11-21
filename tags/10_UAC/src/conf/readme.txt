1,上品公司ldap初始化数据为shopin.net.ldif，数据建立在分区如下：
<jdbmPartition id="shopin" cacheSize="100" suffix="dc=shopin,dc=net" optimizerEnabled="true"
                     syncOnWrite="true">
