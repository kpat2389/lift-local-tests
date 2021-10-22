#!/usr/bin/env python3

import argparse
import json
import os
from pathlib import Path

def emit_version():
    print(1)


def emit_name():
    print("subproject_tool")


def emit_applicable():
    print("true")

def run(path):

    tool_notes = [{
        "type": "SubProject Tool",
        "message": f"Tool ran at path {path}",
        "file": "subproject/SubProject.hs",
        "line": 1
    }]

    print(json.dumps(tool_notes))

def main():
    parser = argparse.ArgumentParser(description='Subproject Tool')
    parser.add_argument('path', metavar='PATH', help='Path to code')
    parser.add_argument('commit_hash', metavar='HASH', help='Commit hash')
    parser.add_argument('command', metavar='COMMAND', help='Command')

    args = parser.parse_args()

    path = args.path

    command = args.command

    if command == "version":
        emit_version()
    elif command == "name":
        emit_name()
    elif command == "applicable":
        emit_applicable()
    elif command == "run":
        run(path)

if __name__ == "__main__":
    main()
