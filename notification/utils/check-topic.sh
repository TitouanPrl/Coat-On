#!/bin/bash
# Read messages in topic
# Run in message broker
kafka-console-consumer --bootstrap-server localhost:9092 --topic userTopicJson --from-beginning --max-messages 10