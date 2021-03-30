PROJECT 		= microservice-intercorp-client
WAR_PATH		= target/
WAR_NAME		= intercorp-microservice-clientz-0.0.1-SNAPSHOT.war
ACCOUNT_ID 		= 417302926719
DEPLOY_REGION 	= us-east-1 # virginia
VERSION 		= $(shell date +%Y%m%d%H%M%S)

test:
	@./mvnw test

package:
	@./mvnw clean install

upload-war: package
	@aws s3 sync $(WAR_PATH) s3://$(PROJECT) --exclude "*" --include $(WAR_NAME)

deploy:
	@echo Creating Bucket S3
	@make deploy-bucket
	@echo Bucket S3 created successfully
	@echo Upload WAR file
	@make upload-war
	@echo WAR file upload successfully
	@echo Deploying BeanStalk Environment
	@make deploy-beanstalk
	@echo BeanStalk Environment created successfully

delete:
	@echo Deleting Bucket S3
	@make delete-bucket
	@echo Bucket S3 deleted successfully
	@echo Deleting BeanStalk Environment
	@make delete-beanstalk
	@echo BeanStalk Environment deleted successfully

deploy-bucket: ## Deploy for first time the bucket for war file
	@aws cloudformation deploy \
		--template-file ./cloudformation/bucketS3.yml \
		--stack-name $(PROJECT)-stack \
		--parameter-overrides \
			S3BucketName=$(PROJECT) \
		--region $(DEPLOY_REGION) \
		--capabilities CAPABILITY_IAM;

delete-bucket: ## Delete the bucket
	@aws s3 rm s3://$(PROJECT) --recursive
	@aws cloudformation delete-stack --stack-name $(PROJECT)-stack

deploy-beanstalk: ## Deploy for first time all application
	@aws cloudformation deploy \
		--template-file ./cloudformation/beanStalk.yml \
		--stack-name $(PROJECT)-app-stack \
		--parameter-overrides \
			ApplicationName=$(PROJECT) \
			BucketS3=$(PROJECT) \
			BucketKey=$(WAR_NAME) \
		--region $(DEPLOY_REGION) \
		--capabilities CAPABILITY_IAM;

delete-beanstalk: ## Delete the application
	@aws cloudformation delete-stack --stack-name $(PROJECT)-app-stack

version: upload-war ## Create new version for app
	aws elasticbeanstalk create-application-version \
		--application-name $(PROJECT) \
 		--version-label $(VERSION) \
 		--description "New Version: $(VERSION)" \
 		--source-bundle S3Bucket="$(PROJECT)",S3Key="$(WAR_NAME)"
