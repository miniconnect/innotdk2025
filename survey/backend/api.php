<?php

$dataDir = getenv('DATA_DIR');
if (empty($dataDir)) {
    echo "No data dir given!";
    exit(1);
}

if (!is_dir($dataDir)) {
    mkdir($dataDir, 0777, true);
}

$payload = file_get_contents('php://input');
$now = date("Y-m-d H:i:s");

file_put_contents($dataDir . '/payloads.log', $now . "\t" . $payload . PHP_EOL, FILE_APPEND);
http_response_code(204);
