/* Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License. */

import { Leaf, Search } from '@vicons/ionicons5'
import type { Router } from 'vue-router'
import List from './components/list'
import styles from './index.module.scss'
import Modal from '@/components/modal'
import { useCDCStore } from '@/store/cdc'
import { type CdcJobDefinition, type CdcJobSubmit, cancelCdcJob, freshCdcJobStatus, submitCdcJob } from '@/api/models/cdc'
import { JobStatus } from '@/api/models/cdc/types/cdcJob'

export default defineComponent({
  name: 'CDCPage',
  setup() {
    const { t } = useLocaleHooks()
    const message = useMessage()
    const dialog = useDialog()
    const showModalRef = ref(false)
    const showSubmitCdcJobModalRef = ref(false)
    const filterValue = ref()
    const CDCStore = useCDCStore()
    const router: Router = useRouter()
    const CDCModalRef = ref()
    const submitCdcJobModalRef = ref()
    const handleConfirm = async (model: any) => {
      CDCStore.setModel(model)
      await CDCModalRef.value.formRef.validate()
      showModalRef.value = false
      router.push({ path: '/cdc_ingestion/dag' })
    }

    const cdcJobTableRef = ref()
    // the actionJobMap member is stateful
    const actionJobMap = new Map<number, CdcJobDefinition>()
    const refreshStatusMap = new Map<number, number>()
    const INTERVAL_MILLISECOND = 1000

    function handleOpenModal() {
      showModalRef.value = true
    }

    function freshStatus(row: CdcJobDefinition, logId: number) {
      const id = row.id
      if (id) {
        freshCdcJobStatus(id, logId).then((res: any) => {
          const status = res.data
          if (status !== JobStatus.SUBMITTING && status !== JobStatus.CANCELLING && status !== row.currentStatus) {
            // fresh target row status
            row.currentStatus = status
            actionJobMap.delete(id)
            const refreshJobStatusIntervalId = refreshStatusMap.get(id)
            if (refreshJobStatusIntervalId) {
              clearInterval(refreshJobStatusIntervalId)
              refreshStatusMap.delete(id)
            }
            message.success(t('cdc.cdc_job_exe_success'))
          }
        }).catch((res) => {
          message.error(res.msg)
        })
      }
    }

    function watchStatus(id: number, logId: number) {
      const targetRow = actionJobMap.get(id)
      let refreshJobStatusIntervalId = refreshStatusMap.get(id)
      if (targetRow && !refreshJobStatusIntervalId) {
        refreshJobStatusIntervalId = setInterval(() => {
          freshStatus(targetRow, logId)
        }, INTERVAL_MILLISECOND)
        refreshStatusMap.set(id, refreshJobStatusIntervalId)
      }
    }

    function toUpdateJobStatus(id: number, res: any) {
      const data = res.data
      const logId = data.logId
      const targetRow = actionJobMap.get(id)
      if (targetRow) {
        // update status
        targetRow.currentStatus = data.jobStatus
        // cron interval to fresh status
        if (targetRow.currentStatus === JobStatus.SUBMITTING
          || targetRow.currentStatus === JobStatus.CANCELLING) {
          watchStatus(id, logId)
        }
        else {
          // remove the target row in actionJobMap
          actionJobMap.delete(id)
        }
      }
    }

    function handleCdcSubmitConfirm(form: CdcJobSubmit) {
      const CDCStore = useCDCStore()
      const id = CDCStore.getModel.id
      // show submit status
      const row = actionJobMap.get(id)
      if (row) {
        row.currentStatus = JobStatus.SUBMITTING
      }
      // to submit cdc job
      submitCdcJob(id, form).then((res) => {
        toUpdateJobStatus(id, res)
      })
      // close modal dialog
      showSubmitCdcJobModalRef.value = false
    }

    function handleSearchCdcJobTable() {
      cdcJobTableRef.value.getTableData(filterValue.value)
    }

    function showSubmitCdcJobModal(row: any) {
      // put the target row to action map
      actionJobMap.set(row.id, row)
      showSubmitCdcJobModalRef.value = true
    }

    function handleCancelCdcJob(row: any) {
      const id = row.id
      cancelCdcJob(id).then((res) => {
        toUpdateJobStatus(id, res)
      })
    }

    function showCancelCdcJobMessage(row: any) {
      dialog.warning({
        title: t('cdc.confirm_title'),
        content: t('cdc.confirm_cancel_content'),
        positiveText: t('cdc.confirm_sure'),
        negativeText: t('cdc.confirm_cancel'),
        onPositiveClick: () => {
          actionJobMap.set(row.id, row)
          handleCancelCdcJob(row)
        },
      })
    }

    return {
      t,
      showModalRef,
      showSubmitCdcJobModalRef,
      showSubmitCdcJobModal,
      showCancelCdcJobMessage,
      handleOpenModal,
      handleConfirm,
      CDCModalRef,
      submitCdcJobModalRef,
      handleCdcSubmitConfirm,
      cdcJobTableRef,
      handleSearchCdcJobTable,
      filterValue,
    }
  },
  render() {
    return (
      <div class={styles['cdc-page']}>
        <n-card>
          <n-space vertical size={24}>
            <n-card>
              <div class={styles.title}>
                <n-space align="center">
                  <n-icon component={Leaf} color="#2F7BEA" size="18" />
                  <span>{this.t('cdc.cdc_job_definition')}</span>
                </n-space>
                <div class={styles.operation}>
                  <n-space>
                    <n-input
                      placeholder={this.t('playground.search')}
                      v-model:value={this.filterValue}
                      v-slots={{
                        prefix: () => <n-icon component={Search} />,
                      }}
                      onBlur={this.handleSearchCdcJobTable}
                    />
                    <n-button type="primary" onClick={this.handleOpenModal}>
                      {this.t('cdc.create_synchronization_job')}
                    </n-button>
                  </n-space>
                </div>
              </div>
            </n-card>
            <List
              ref="cdcJobTableRef"
              onCdcJobSubmit={row => (this.showSubmitCdcJobModal(row))}
              onCdcJobCancel={row => (this.showCancelCdcJobMessage(row))}
            />
            {this.showModalRef && (
              <Modal
                ref="CDCModalRef"
                showModal={this.showModalRef}
                title={this.t('cdc.create_synchronization_job')}
                formType="CDCLIST"
                onCancel={() => (this.showModalRef = false)}
                onConfirm={this.handleConfirm}
              />
            )}
            {this.showSubmitCdcJobModalRef && (
              <Modal
                ref="submitCdcJobModalRef"
                showModal={this.showSubmitCdcJobModalRef}
                title={this.t('cdc.submit_cdc_job')}
                formType="CDCSUBMIT"
                onCancel={() => (this.showSubmitCdcJobModalRef = false)}
                onConfirm={this.handleCdcSubmitConfirm}
              />
            )}
          </n-space>
        </n-card>
      </div>
    )
  },
})
