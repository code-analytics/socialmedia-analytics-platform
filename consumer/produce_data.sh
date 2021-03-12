kafka-topics --create zookeeper localhost:2181 replication-factor 1 --partitions 9 --topic test

kafka-console-producer.sh --broker-list localhost:9092 --topic test