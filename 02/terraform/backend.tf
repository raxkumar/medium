terraform {
  backend "http" {
    address =         "http://localhost:9080/terraform-state?key=backend"
    lock_address =    "http://localhost:9080/terraform-state/lock?key=backend"
    unlock_address =  "http://localhost:9080/terraform-state/unlock?key=backend"
  }
}



terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 2.13.0"
    }
  }
}

provider "docker" {}

resource "docker_image" "nginx" {
  name         = "nginx:latest"
  keep_locally = false
}

resource "docker_container" "nginx" {
  image = docker_image.nginx.latest
  name  = "nginx_test_image"
  ports {
    internal = 80
    external = 8000
  }
}
