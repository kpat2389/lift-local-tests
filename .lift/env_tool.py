#!/usr/bin/env python3

import argparse
import json
import os

def emit_version():
    print(1)


def emit_name():
    print("env_tool")


def emit_applicable():
    print("true")

def run(path):
    env = dict(os.environ)

    tool_notes = []
    for key, value in env.items():
        tool_notes.append(env_to_tool_note(key, value))

    print(json.dumps(tool_notes))

def env_to_tool_note(key, value):
    return {
        "type": "Env Tool",
        "message": key,
        "file": "README.md",
        "line": 1
    }

def main():
    parser = argparse.ArgumentParser(description='Env Tool')
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
