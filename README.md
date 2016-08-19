- install vagrant http://www.vagrantup.com/
- install oracle VM VirtualBox Manager http://www.oracle.com/technetwork/server-storage/virtualbox/downloads/index.html
- install putty or some ssh client

1. Vagrant hbase
1.1 In local project work directory 'phr-poc\hbase' run command 'vagrant up'
If everything is ok, then you should see "hbase_defaul_..." Running in Oracle VM VirtualBox

1.2 Putty ssh 127.0.0.1:2222
vagrant/vagrant

1.2.1 Check whether necessary Hadoopi and Hbase processes are running:
vagrant@vagrant-hbase:~$ jps
Hbase:
14525 HRegionServer
14393 HQuorumPeer
14446 HMaster
Hadoop:
13761 DataNode
13940 SecondaryNameNode
13641 NameNode

1.2.2 Stopping/starting Hadoop
1.2.2.1 Stopping
vagrant@vagrant-hbase:~/hadoop/sbin$ ./stop-dfs.sh
1.2.2.2 Starting
vagrant@vagrant-hbase:~/hadoop/sbin$ ./start-dfs.sh

1.2.3 Stopping/starting Hbase 
1.2.3.1 Stopping
vagrant@vagrant-hbase:~/hbase/bin$ ./stop-hbase.sh
1.2.3.1 Starting
vagrant@vagrant-hbase:~/hbase/bin$ ./start-hbase.sh

1.2.4 Configuration changes
In directory '~/hbase/bin' edit file 'hbase-site.xml'

change from:
    <property>
        <name>hbase.zookeeper.quorum</name>
        <value>localhost</value>
    </property>
to:
    <property>
        <name>hbase.zookeeper.quorum</name>
        <value>127.0.0.1</value>
    </property>

1.2.5 Starting Hbase shell 
vagrant@vagrant-hbase:~/hbase/bin$ ./hbase shell

1.2.5.1 Create datastructures
phr-poc/src/main/resources/data_structures.txt
Initial:
create_namespace 'phr'

create 'phr:organisation', 'id'
create 'phr:person', 'id'
create 'phr:resource', 'id' 

1.3 Testing connection
1.3.1 You shuld see response from:
http://192.168.15.166:60010/master-status

1.3.2 In IDE run TestPersonService as Junit test
Test should end successfully (ignore exception java.io.IOException: Could not locate executable null\bin\winutils.exe in the Hadoop binaries.)

