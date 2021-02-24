package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Table struct {
	paper chan int
	grass chan int
	match chan int
}

var wg *sync.WaitGroup
var table *Table

const (
	paper = iota
	grass
	match
)

func smoker(signal <-chan int, ingredient int) {
	var nextSmoker int
	for {
		nextSmoker = <-signal
		if nextSmoker != ingredient {
			continue
		}
		switch nextSmoker {
		case match:
			<-table.grass
			<-table.paper
			fmt.Println("Smoker(match) is smoking....")
		case grass:
			<-table.paper
			<-table.match
			fmt.Println("Smoker(grass) is smoking....")
		case paper:
			<-table.grass
			<-table.match
			fmt.Println("Smoker(paper) is smoking....")
		}
		time.Sleep(time.Millisecond * 500)
		wg.Done()
	}
}

func manager(done chan<- bool, signals [3]chan int) {
	var nextSmoker int
	for i := 0; i < 10; i++ {
		nextSmoker = rand.Intn(3)
		switch nextSmoker {
		case paper:
			fmt.Println("Table provided grass and match")
			table.grass <- grass
			table.match <- match
		case grass:
			fmt.Println("Table provided paper and match")
			table.paper <- paper
			table.match <- match
		case match:
			fmt.Println("Table provided grass and paper")
			table.paper <- paper
			table.grass <- grass
		}
		for _, signal := range signals {
			signal <- nextSmoker
		}
		wg.Add(1)
		wg.Wait()
	}
	done <- true
}

const LIMIT = 1

func main() {
	wg = new(sync.WaitGroup)
	table = new(Table)
	table.match = make(chan int, LIMIT)
	table.grass = make(chan int, LIMIT)
	table.paper = make(chan int, LIMIT)
	done := make(chan bool)
	var signals [3]chan int
	for i := 0; i < 3; i++ {
		signal := make(chan int, 1)
		signals[i] = signal
	}

	go smoker(signals[0], paper)
	go smoker(signals[1], grass)
	go smoker(signals[2], match)

	go manager(done, signals)
	<-done
}
